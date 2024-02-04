package com.genshin.ojquestion.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojapi.client.RunClient;
import com.genshin.ojcommon.common.ErrorCode;
import com.genshin.ojcommon.constants.MqConstants;
import com.genshin.ojcommon.domain.dto.SubmitRecordsDto;
import com.genshin.ojcommon.domain.dto.mq.SubmitQuestionWithTokenDto;
import com.genshin.ojcommon.domain.dto.question.JudgeCase;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.dto.question.QuestionSubmitAddRequest;
import com.genshin.ojcommon.domain.entity.LoginUser;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.enums.QuestionSubmitLanguageEnum;
import com.genshin.ojcommon.domain.enums.QuestionSubmitStatusEnum;
import com.genshin.ojcommon.domain.vo.SolveVo;
import com.genshin.ojcommon.domain.vo.SubmitVo;
import com.genshin.ojcommon.exception.BusinessException;
import com.genshin.ojcommon.producer.MyMessageProducer;
import com.genshin.ojcommon.utils.JsonTransforUtils;
import com.genshin.ojcommon.utils.RedisCache;
import com.genshin.ojquestion.mapper.QuestionMapper;
import com.genshin.ojquestion.mapper.SubmitRecordsMapper;
import com.genshin.ojquestion.service.QuestionService;
import com.genshin.ojquestion.service.SubmitRecordsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.genshin.ojcommon.constants.RedisConstants.GET_SOLVE_LANGUAGE_NUM;

/**
 * @author 吴嘉豪
 * @date 2023/12/30 15:32
 */
@Service
public class SubmitRecordsServiceImpl extends ServiceImpl<SubmitRecordsMapper, SubmitRecords> implements SubmitRecordsService {
    @Autowired
    private SubmitRecordsMapper submitRecordsMapper;
    @Autowired
    private QuestionService questionService;
    @Resource
    private MyMessageProducer myMessageProducer;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RunClient runClient;

    @Override
    public void insert(String id, String location, Integer status, Long lastReason, Integer lastCase, Long titleId) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        SubmitRecords submitRecords = new SubmitRecords();
        submitRecords.setPid(userId);
//        submitRecords.setLocation(location);
        submitRecords.setStatus(0);
        submitRecords.setLastReason(0L);
        submitRecords.setQuestionId(titleId);

        if(status == 0){
            submitRecords.setLastCase(-1);
        }
        else submitRecords.setLastCase(lastCase);
        submitRecordsMapper.insert(submitRecords);
    }

    @Override
    public List<SubmitRecordsDto> queryById(Long id, Long titleId) {
        List<SubmitRecords> submitRecordsList = list(new LambdaQueryWrapper<SubmitRecords>().eq(SubmitRecords::getId, id).eq(SubmitRecords::getQuestionId, titleId));
        List<SubmitRecordsDto> collect = submitRecordsList.stream().map(e -> {
            SubmitRecordsDto records = new SubmitRecordsDto();
            BeanUtils.copyProperties(e, records);
            return records;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<SubmitRecordsDto> queryByIdWithoutTitleId(Long id) {
        List<SubmitRecords> submitRecordsList = list(new LambdaQueryWrapper<SubmitRecords>().eq(SubmitRecords::getId, id));
        List<SubmitRecordsDto> collect = submitRecordsList.stream().map(e -> {
            SubmitRecordsDto records = new SubmitRecordsDto();
            BeanUtils.copyProperties(e, records);
            return records;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Long runQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, LoginUser user, String token) {
        Integer languageId = questionSubmitAddRequest.getLanguage();
        String language =  QuestionSubmitLanguageEnum.getEnumByKey(languageId);
        if((languageId < 0 || StrUtil.isEmpty(language)) && QuestionSubmitLanguageEnum.getCount() <= languageId)throw new  BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");

        Long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 增加这道题目的提交数
        boolean update = questionService.update().setSql("submit_num = submit_num + 1").eq("id", questionId).update();
        if(!update) throw new BusinessException(ErrorCode.OPERATION_ERROR, "保存数据失败");
        Long id = user.getUser().getId();
        SubmitRecords submitRecords = new SubmitRecords();
        submitRecords.setPid(id);
        submitRecords.setCode(questionSubmitAddRequest.getCode());
        submitRecords.setLanguage(language);
        submitRecords.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        submitRecords.setJudgeInfo("{}");
        submitRecords.setQuestionId(questionId);
        boolean save = this.save(submitRecords);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        Long submitId = submitRecords.getId();
        SubmitQuestionWithTokenDto tokenDto = new SubmitQuestionWithTokenDto(submitId.toString(), token);
        myMessageProducer.sendMessage(MqConstants.RUN_EXCHANGE, MqConstants.RUN_CODE_KEY, tokenDto);
////        // 执行判题服务
//        CompletableFuture.runAsync(() -> {
        Integer value = redisCache.getCacheMapValue(GET_SOLVE_LANGUAGE_NUM + ":" + user.getUser().getId(), language);
        if(value == null) redisCache.setCacheMapValue(GET_SOLVE_LANGUAGE_NUM + ":" + user.getUser().getId(), language, 0);
        redisCache.incrCacheMapValue(GET_SOLVE_LANGUAGE_NUM + ":" + user.getUser().getId(), language, 1);
//        });
        return submitId;
    }

    @Override
    public List<SolveVo> getSolveVoByScore(int score, Long userId) {
        // lt score &&  userId  所有元素 && status === 2 // 判断 是否成功通过JudgeInfo 来进行 ，然后统计
        List<SolveVo> vos = new ArrayList<>();
        for(int i = 0; i < 3; ++i){
            SolveVo vo = new SolveVo(0, 0);
            vos.add(vo);
        }
        List<SubmitRecords> submitRecords = getRecordsList(userId);
        for (SubmitRecords records : submitRecords) {
            JudgeInfo submitJudgeInfo = JsonTransforUtils.JsonToObj(JudgeInfo.class, records.getJudgeInfo());
            Long questionId = records.getQuestionId();
            Integer getScore =  questionService.getScoreById(questionId);
            int whichScore = 0;
            if(getScore < 1500) whichScore = 0;
            else if(getScore < 2100) whichScore = 1;
            else whichScore = 2;
            // 关于为什么可能会等于空值，还在调试， 这里先进行一次判断
            if(submitJudgeInfo != null && submitJudgeInfo.getMessage() != null &&  submitJudgeInfo.getMessage().equals("成功")){
                vos.get(whichScore).setPassNum(vos.get(whichScore).getPassNum() + 1);

            }
            vos.get(whichScore).setTryNum(vos.get(whichScore).getTryNum() + 1);
        }
        return vos;
    }
    public Integer getPassMessageCount(boolean isNeedPass, Long questionId, Long userId){
        LambdaQueryWrapper<SubmitRecords> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SubmitRecords::getStatus, 2);
        wrapper.eq(userId != -1, SubmitRecords::getPid, userId);
        wrapper.eq(SubmitRecords::getQuestionId,questionId);
        wrapper.like(true,  SubmitRecords::getJudgeInfo, "成功");
        Integer count = submitRecordsMapper.selectCount(wrapper);
        return count;
    }
    @Override
    public List<SolveVo> getPassMessage(long userId) {
        List<SolveVo> vos = new ArrayList<>();
        for(int i = 0; i < 3; ++i){
            SolveVo vo = new SolveVo(0, 0);
            vos.add(vo);
        }

        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getQuestionType, 0);
        queryWrapper.select(Question::getId, Question::getScore);

        List<Question> questions = questionMapper.selectList(queryWrapper);
        for (Question question : questions) {
            Long id = question.getId();
            Integer getScore = question.getScore();
            Integer passMessageCount = getPassMessageCount(true, id, userId);
            Integer totalMessageCount = getPassMessageCount(false, id, userId);
            int whichScore = 0;
            if(getScore < 1500) whichScore = 0;
            else if(getScore < 2100) whichScore = 1;
            else whichScore = 2;
            if(passMessageCount > 0){
                vos.get(whichScore).setPassNum(vos.get(whichScore).getPassNum() + 1);
            }
            vos.get(whichScore).setTryNum(vos.get(whichScore).getTryNum() + totalMessageCount);
        }

        return vos;
    }

    @Override
    public SubmitRecords getTheLatestMsg(LambdaQueryWrapper<SubmitRecords> wrapper) {
        return submitRecordsMapper.selectOne(wrapper);
    }

    @Override
    public List<SubmitVo> getSubmitRecordByUserIdAndQuestionId(String userId, String questionId) {
        LambdaQueryWrapper<SubmitRecords> wrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isEmpty(userId)) throw  new BusinessException(ErrorCode.PARAMS_ERROR, "用户参数传递值为空");
        wrapper.eq(SubmitRecords::getPid, userId);
        wrapper.eq(StrUtil.isEmpty(questionId) == false,  SubmitRecords::getQuestionId, questionId);
        wrapper.eq(SubmitRecords::getStatus, 2);
        wrapper.orderByDesc(SubmitRecords::getCreateTime);
        List<SubmitRecords> submitRecords = submitRecordsMapper.selectList(wrapper);

        return submitRecords.stream().map(e-> getSubmitVoByEvalue(e)).collect(Collectors.toList());
    }

    private static SubmitVo getSubmitVoByEvalue(SubmitRecords e) {
        SubmitVo submitVo = new SubmitVo();
        String judgeInfo = e.getJudgeInfo();

        JudgeInfo info = JsonTransforUtils.JsonToObj(JudgeInfo.class, judgeInfo);
        BeanUtils.copyProperties(e, submitVo);
        if(info == null) {
            submitVo.setMemory(String.valueOf((0)));
            submitVo.setTime(String.valueOf(0));
            submitVo.setAllStatus("系统内部错误");
        }
        else{
            submitVo.setMemory(info.getMemory().toString());
            submitVo.setTime(info.getTime().toString());
            submitVo.setAllStatus(info.getMessage());
        }

        return submitVo;
    }

    @Override
    public List<SubmitVo> queryPage(Integer page, Integer pageSize, String userId, String questionId) {
        Page<SubmitRecords> recordsPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<SubmitRecords> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isEmpty(questionId) == false, SubmitRecords::getQuestionId, questionId);
        wrapper.eq(SubmitRecords::getPid, userId);
        wrapper.orderByDesc(SubmitRecords::getCreateTime);
        wrapper.eq(SubmitRecords::getStatus, 2);
//        LambdaQueryWrapper<SubmitRecords> wrapper = getWrapper(Long.valueOf(userId)).eq(StrUtil.isEmpty(questionId) == false,  SubmitRecords::getQuestionId, questionId).orderByDesc(SubmitRecords::getCreateTime);
        submitRecordsMapper.selectPage(recordsPage, wrapper);
        List<SubmitRecords> records = recordsPage.getRecords();
        List<SubmitVo> collect = records.stream().map(e -> {
            Long id = e.getQuestionId();
            LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(Question::getTitleName, Question::getTitleId);
            queryWrapper.eq(Question::getId, id);
            Question question = questionMapper.selectOne(queryWrapper);
            SubmitVo submitVoByEvalue = getSubmitVoByEvalue(e);
            submitVoByEvalue.setTitleId(question.getTitleId());
            submitVoByEvalue.setTitleName(question.getTitleName());
            return submitVoByEvalue;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public SubmitRecords getLatestSubmit(String userId) {
        return submitRecordsMapper.getLatestSubmit(userId);
    }

    private List<SubmitRecords> getRecordsList(Long userId) {
        LambdaQueryWrapper<SubmitRecords> wrapper = getWrapper(userId);
        List<SubmitRecords> submitRecords = submitRecordsMapper.selectList(wrapper);
        return submitRecords;
    }

    private static LambdaQueryWrapper<SubmitRecords> getWrapper(Long userId) {
        LambdaQueryWrapper<SubmitRecords> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SubmitRecords::getStatus, 2).eq(userId != -1,  SubmitRecords::getPid, userId);
        wrapper.select(SubmitRecords::getJudgeInfo, SubmitRecords::getQuestionId);
        return wrapper;
    }


}
