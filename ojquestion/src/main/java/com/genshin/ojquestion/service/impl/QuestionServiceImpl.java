package com.genshin.ojquestion.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojapi.client.UserClient;
import com.genshin.ojcommon.domain.dto.question.JudgeCase;
import com.genshin.ojcommon.domain.dto.question.JudgeConfig;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.dto.question.QuestionAddRequest;
import com.genshin.ojcommon.domain.entity.LoginUser;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.vo.QuestionManaReturnVo;
import com.genshin.ojcommon.domain.vo.QuestionReturnVo;
import com.genshin.ojcommon.domain.vo.QuestionShowVo;
import com.genshin.ojcommon.domain.vo.SubmitProfileVo;
import com.genshin.ojcommon.utils.JsonTransforUtils;
import com.genshin.ojcommon.utils.RedisCache;
import com.genshin.ojquestion.mapper.*;
import com.genshin.ojquestion.service.QuestionService;
import com.genshin.ojcommon.utils.FileToTextUtils;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojquestion.utils.MinioUtils;
import com.rabbitmq.client.AMQP;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.genshin.ojcommon.constants.RedisConstants.*;

/**
 * @author 吴嘉豪
 * @date 2023/12/19 13:00
 */
@Service
public  class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private MinioUtils minioUtils;
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TagListMapper tagListMapper;
    @Autowired
    private SubmitRecordsMapper submitRecordsMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserClient userClient;
    // 锁对象
    private final Object lock = new Object();
    @Override
    public void uploadFile(String fileName, Integer score, String createBy) {
        Question question = new Question();
        question.setQuestionLocation("oj-question-file");
        question.setFileName(fileName);
        question.setQuestionType(4);
        question.setScore(score);
        question.setCreateBy(createBy);
        question.setLikes(0);
        question.setPassPerson(0);
        question.setTitleName(createBy + ":without");
        save(question);
    }

    @Override
    public void removeByIdAndBuck(Long userId) {
        Question question = getQuestion(userId);
        if(question == null) return ;
        questionMapper.deleteById(question.getId());
        // 移除掉网络上那些有问题的东西
        minioUtils.removeObject(question.getFileName());
        System.out.printf("删除成功");
    }

    @Override
    public boolean updateProblemStatus(Long userId, QuestionAddRequest questionAddRequest) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.le(Question::getQuestionType, 4).eq(Question::getTitleName, questionAddRequest.getTitleName());
        Integer i = questionMapper.selectCount(wrapper);
        if(i > 0) return false;
        Question question = getQuestionByUserId(userId);
        List<JudgeCase> judgeCase = questionAddRequest.getJudgeCase();
        List<Integer> tags = questionAddRequest.getTags();
        List<String> language = questionAddRequest.getLanguage();
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        BeanUtils.copyProperties(questionAddRequest,question);
        question.setJudgeCase(JsonTransforUtils.ArrayToJson(judgeCase));
        question.setJudgeConfig(JSON.toJSONString(judgeConfig));
        question.setTags(JsonTransforUtils.ArrayToJson(tags));
        question.setTitleName(questionAddRequest.getTitleName());

        // 进入审核状态
        question.setQuestionType(3);
        question.setLanguage(JsonTransforUtils.ArrayToJson(language));
        System.out.println(question);
        synchronized (lock){
            question.setTitleId(questionMapper.selectCount(new LambdaQueryWrapper<Question>().le(Question::getQuestionType, 3)) + 1);
            questionMapper.updateById(question);
        }
        return true;
    }

    @Override
    public List<QuestionManaReturnVo> showRequest(int page, int pageSize) {
        // 构造页数器
        List<Question> records = getPageRecords(page, pageSize, false);
        List<QuestionManaReturnVo> list = records.stream().map(e -> {
//            Integer passPerson = (Integer)redisCache.getCacheObject(PASS_PROBLEM_PERSON + ":"+ id.toString());
//            Integer tryPerson = (Integer) redisCache.getCacheObject(TRY_PROBLEM_PERSON + ":" + id.toString());
//            if(passPerson == null){
//
//                redisCache.setCacheObject(PASS_PROBLEM_PERSON + ":"+ id.toString(), 0);
//                redisCache.setCacheObject(TRY_PROBLEM_PERSON + ":"+ id.toString(), 0);
//
//            }
            Integer passPerson = e.getPassPerson();
            Integer tryPerson = e.getSubmitNum();
            QuestionManaReturnVo questionManaReturnDto = new QuestionManaReturnVo();
            BeanUtils.copyProperties(e, questionManaReturnDto);
            //将他转换为String类型， 避免精度溢出
            questionManaReturnDto.setPassPerson(passPerson);
            questionManaReturnDto.setTryPerson(tryPerson);
            return questionManaReturnDto;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * @note 是否需要添加限制类型
     * @param page
     * @param pageSize
     * @param needType
     * @return
     */
    private List<Question> getPageRecords(int page, int pageSize, boolean needType) {
        Page<Question> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(Question::getQuestionType);
        queryWrapper.select(
                Question::getId,
                Question::getUpdateTime,
                Question::getCreateTime,
                Question::getQuestionType,
                Question::getPassPerson,
                Question::getSubmitNum,
                Question::getCreateBy,
                Question::getLikes,
                Question::getFavourNum,
                Question::getTags,
                Question::getTitleId,
                Question::getTitleName,
                Question::getScore
        );
        if(needType)queryWrapper.eq(Question::getQuestionType, 0);
        page(pageInfo, queryWrapper);
        List<Question> records = pageInfo.getRecords();
        return records;
    }

    @Override
    public List<QuestionReturnVo> showRequestForVip(int page, int pageSize) {
        List<Question> pageRecords = getPageRecords(page, pageSize, true);
        List<QuestionReturnVo> collect = pageRecords.stream().map(e -> {
            String id = e.getId().toString();
            String tags = e.getTags();
            List<Long> integers = JsonTransforUtils.JsonToArray(Long.class, tags);
            List<String> tagList = integers.stream().map(tagListMapper::getTagName).collect(Collectors.toList());
            QuestionReturnVo questionReturnDto = new QuestionReturnVo();
            BeanUtils.copyProperties(e, questionReturnDto);
            questionReturnDto.setTags(tagList);
            // TODO 判断该用户是否完成了这道题目
            questionReturnDto.setStatus(QuestionPass(e.getId()));
            questionReturnDto.setTryStatus(QuestionTry(e.getId()));
            return questionReturnDto;
        }).collect(Collectors.toList());
        return collect;
    }

    private boolean QuestionTry(Long questionId) {
        Long userId = GetLoginUserUtils.getUserId();
        // 判断题目是否通过， 如果通过的话， 那么就是选择2
        LambdaQueryWrapper<SubmitRecords> wrapper = new LambdaQueryWrapper<SubmitRecords>().eq(SubmitRecords::getPid, userId).eq(SubmitRecords::getStatus, 2).eq(SubmitRecords::getQuestionId, questionId);
        Integer count = submitRecordsMapper.selectCount(wrapper);
        return count > 0 ? true : false;
    }

    public boolean QuestionPass( Long questionId){
        Long userId = GetLoginUserUtils.getUserId();
        // 判断题目是否通过， 如果通过的话， 那么就是选择2
        LambdaQueryWrapper<SubmitRecords> wrapper = new LambdaQueryWrapper<SubmitRecords>().eq(SubmitRecords::getPid, userId).eq(SubmitRecords::getStatus, 2).eq(SubmitRecords::getQuestionId, questionId).like(SubmitRecords::getJudgeInfo, "成功");
        Integer count = submitRecordsMapper.selectCount(wrapper);
        return count > 0 ? true : false;

//        return i > 0 ? true : false;
    }
    @Override
    public boolean passQuestion(String id) {

        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getId, id);
        wrapper.select(Question::getScore);
        Question question = questionMapper.selectOne(wrapper);
        Integer score = question.getScore();
        int key = 0;
        if(score < 1500) {
            key = 0;
        } else if (score < 2100) {
            key = 1;
        }
        else key = 2;
        // 根据分值来添加题目
        redisTemplate.opsForHash().increment(QUESTION_NUMS, String.valueOf(key), 1);
        return update().setSql("question_type=0").eq("id", id).update();
    }

    @Override
    public boolean removeByTitle(String titleName) {
        return update().setSql("question_type=2").eq("title_name", titleName).update();
    }

    @Override
    public boolean removeByIdU(String id) {
        return update().setSql("question_type=2").eq("id", id).update();
    }

    @Override
    public void saveFile(MultipartFile file) {
        LoginUser user = GetLoginUserUtils.getLoginUser();
        String text = FileToTextUtils.getText(file);
        Question question = new Question();
        question.setPassPerson(0);
        question.setFavourNum(0);
        question.setQuestionType(4);
        question.setContent(text);
        question.setScore(114514);
        question.setTitleName(UUID.randomUUID().toString() + user.getUser().getId());
        question.setSubmitNum(0);
        question.setPassPerson(0);
        question.setLikes(0);
        question.setAns("[]");
        question.setCreateUserId(user.getUser().getId());
        question.setTags("[]");
        question.setJudgeCase("[]");
        question.setCreateBy(user.getUsername());
        // 确保每次只有一个线程进来， 避免题目Id 出现重复问题
        synchronized (lock){
            question.setTitleId(count() + 1);
            questionMapper.insert(question);
        }

    }

    @Override
    public void uploadFileWithoutSub(MultipartFile file, Long userId) {
        Question question = getQuestionByUserId(userId);
        if(question == null){
            saveFile(file);
        }
        else{
            String text = FileToTextUtils.getText(file);
            question.setContent(text);
            questionMapper.updateById(question);
            System.out.println("完成");
        }
    }

    @Override
    public QuestionShowVo getQuestionContent(String id, String token) {
        Question question = questionMapper.selectById(id);
        QuestionShowVo questionShowVo = new QuestionShowVo();
        BeanUtils.copyProperties(question, questionShowVo);
        questionShowVo.setLanguage(JsonTransforUtils.JsonToArray(String.class, question.getLanguage()));
        questionShowVo.setJudgeConfig(JsonTransforUtils.JsonToObj(JudgeConfig.class, question.getJudgeConfig()));
        // openfeign  调用 user 的方法来查询,  不需要了直接使用 jwt 实现即可, 后续在进行拓展
        String nickNameById = userClient.getNickNameById(question.getCreateUserId(), token);
        questionShowVo.setTags(JsonTransforUtils.JsonToArray(String.class, question.getTags()));
        questionShowVo.setCreateBy(nickNameById);
        questionShowVo.setStatus(QuestionPass(question.getId()));
        Long userId = GetLoginUserUtils.getUserId();
        QuestionShowVo t =  userClient.getThumbAndFavourByUserId(id, userId, token);
        questionShowVo.setIsFavour(t.getIsFavour());
        questionShowVo.setIsThumb(t.getIsThumb());
//        BeanUtils.copyProperties(t, questionShowVo);
//        questionShowVo.setIsFavour();
        return questionShowVo;
    }

    @Override
    public Question getByUserId(Long userId) {
        Question question = getQuestionByUserId(userId);
        return question;
//        return getQuestion(userId);
    }

    @Override
    public QuestionShowVo getQuestionContentByTitleName(String titleName, String token) {

        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getQuestionType, 4);
        wrapper.eq(Question::getTitleName, titleName);
        Question question = questionMapper.selectOne(wrapper);
        QuestionShowVo questionShowVo = new QuestionShowVo();
        BeanUtils.copyProperties(question, questionShowVo);
        questionShowVo.setLanguage(JsonTransforUtils.JsonToArray(String.class, question.getLanguage()));
        questionShowVo.setJudgeConfig(JsonTransforUtils.JsonToObj(JudgeConfig.class, question.getJudgeConfig()));
//        // openfeign  调用 user 的方法来查询,  不需要了直接使用 jwt 实现即可, 后续在进行拓展
        Long userId = question.getCreateUserId();
        String nickNameById = userClient.getNickNameById(userId, token);
        questionShowVo.setCreateBy(nickNameById);
        return questionShowVo;
    }

    @Override
    public Integer getScoreById(Long questionId) {
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Question::getScore);
        queryWrapper.eq(Question::getId, questionId);
        Question question = questionMapper.selectOne(queryWrapper);
        return question.getScore();
    }

    @Override
    public List<Integer> getCountByWrapper() {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getQuestionType, 0);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for(int i = 0; i < 3; ++i){
            int score = 1500;
            if(i == 1) score = 2100;
            else score = 3500;
            wrapper.lt(Question::getScore,score);
            arrayList.add(questionMapper.selectCount(wrapper));
        }
        return arrayList;
//        return questionMapper.selectCount(wrapper);
    }

    @Override
    public boolean isPassThisQuestionByUserId(Long questionId, Long userId) {
        LambdaQueryWrapper<SubmitRecords> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SubmitRecords::getStatus, 2);
        wrapper.eq(userId != -1, SubmitRecords::getPid, userId);
        wrapper.eq(SubmitRecords::getQuestionId,questionId);
        wrapper.like(true,  SubmitRecords::getJudgeInfo, "成功");
        Integer count = submitRecordsMapper.selectCount(wrapper);
        return count > 0;
    }

    @Override
    public Long getRandomProblem(HttpServletRequest request) {
        String token = request.getHeader("token");
        Question question = questionMapper.getRandomProblem();
        return question.getId();

    }

    @Override
    public Long getNextOrLast(String id, Integer direction) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getId, id);
        wrapper.select(Question::getTitleId);
        Question question = questionMapper.selectOne(wrapper);
        Integer count =  count(new LambdaQueryWrapper<Question>().eq(Question::getQuestionType, 0));
        Integer titleId = question.getTitleId();
        Integer nextId = (titleId + direction + count) % count + 1;
        // 根据nextId 来查询 字段
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getQuestionType,0);
        queryWrapper.eq(Question::getTitleId, nextId);
        queryWrapper.select(Question::getId);
        Question question1 = questionMapper.selectOne(queryWrapper);
        return question1.getId();
    }

    @Override
    public List<SubmitProfileVo> getProfileSubmitList() {
        Long userId = GetLoginUserUtils.getUserId();
        List<SubmitProfileVo> list =  submitRecordsMapper.getProfileSubmitList(userId, "成功");
        List<SubmitProfileVo> collect = list.stream().map(e -> {
            Long questionId = e.getId();
            e.setScore(getScoreById(questionId));
            Question question =   getTitleById(questionId);
            e.setTitleName(question.getTitleName());
            e.setTitleId(String.valueOf(question.getTitleId()));
            return e;
        }).collect(Collectors.toList());
        return collect;
    }

    private Question getTitleById(Long questionId) {

        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getId, questionId);
        wrapper.select(Question::getTitleId , Question::getTitleName);
        return questionMapper.selectOne(wrapper);
    }

    private Question getQuestionByUserId(Long userId) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getCreateUserId, userId);
        wrapper.eq(Question::getQuestionType, 4);
        Question question = questionMapper.selectOne(wrapper);
        return question;
    }

    public Question getQuestion(Long userId){
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getCreateBy, userId);
        queryWrapper.eq(Question::getQuestionType, 4);
        Question question = questionMapper.selectOne(queryWrapper);
        return question;
    }
}
