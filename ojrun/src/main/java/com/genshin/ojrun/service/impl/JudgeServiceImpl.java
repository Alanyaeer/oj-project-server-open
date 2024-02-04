package com.genshin.ojrun.service.impl;


import com.genshin.ojapi.client.QuestionClient;

import com.genshin.ojcommon.common.ErrorCode;
import com.genshin.ojcommon.domain.dto.codesandbox.*;
import com.genshin.ojcommon.domain.dto.question.JudgeCase;
import com.genshin.ojcommon.domain.dto.question.JudgeContext;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.enums.QuestionSubmitLanguageEnum;
import com.genshin.ojcommon.domain.enums.QuestionSubmitStatusEnum;
import com.genshin.ojcommon.exception.BusinessException;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojcommon.utils.JsonTransforUtils;
import com.genshin.ojcommon.utils.JwtUtil;
import com.genshin.ojcommon.utils.RedisCache;
import com.genshin.ojrun.service.JudgeService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.genshin.ojcommon.constants.RedisConstants.*;

/**
 * @author 吴嘉豪
 * @date 2024/1/12 10:30
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private QuestionClient questionClient;
    @Autowired
    private RedisCache redisCache;
    @Value("${codeSandbox.type}")
    private String type;
    @Autowired
    private JudgeManager judgeManager;
    @Override
    public SubmitRecords doJudge(long questionSubmitId, String token) {
        SubmitRecords submitRecords =  questionClient.getRecordSubmitById(questionSubmitId, token);
        if(submitRecords  == null) throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        Long questionId = submitRecords.getQuestionId();
        Question question =  questionClient.getQuestionById(questionId, token);
        if(question == null) throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目不存在");
        if(!submitRecords.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
//        QuestionSubmit submit = new QuestionSubmit();
        SubmitRecords submit = new SubmitRecords();
        submit.setId(questionSubmitId);
        submit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean isOk =  questionClient.updateSubmitRecordById(submit, token);
        if(!isOk) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新异常");
        }
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = submitRecords.getLanguage();
        String code = submitRecords.getCode();
        // 获取输入用例
        List<JudgeCase> judgeCaseList = JsonTransforUtils.JsonToArray(JudgeCase.class, question.getJudgeCase());

        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(QuestionSubmitLanguageEnum.getKeyToValue(language)).build();

        // 获取到答案 了
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        // 将 判题结果 交给 判题服务去做
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext context = new JudgeContext();
        context.setOutputList(outputList);
        context.setInputList(inputList);
        context.setJudgeCaseList(judgeCaseList);
        context.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        context.setQuestion(question);
        context.setSubmitRecords(submitRecords);
        JudgeInfo info = judgeManager.doJudge(context);
        SubmitRecords newSubmit = new SubmitRecords();
        newSubmit.setJudgeInfo(JsonTransforUtils.ObjToJson(info));
        newSubmit.setId(questionSubmitId);
        newSubmit.setErrorMessage(executeCodeResponse.getMessage());
        newSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        int scoreType = 0;
        Integer score = question.getScore();
        if(score < 1500) scoreType = 0;
        else if( score < 2100) scoreType    = 1;
        else scoreType = 2;
//        Long userId = null;
//        try {
//            Claims claims = JwtUtil.parseJWT(token);
//            userId = Long.valueOf(claims.getSubject());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        Long userId = submitRecords.getPid();
        if(info  != null && info.getMessage()!= null && info.getMessage().equals("成功")){
            // 提交通过的次数增加
            redisCache.incrByCacheObject(PASS_PROBLEM_PERSON + ":" + scoreType, 1);
            redisCache.incrByCacheObject(PASS_PROBLEM_PERSON + ":" + userId + ":" + scoreType, 1);
            // 看看过完有没有成功的
            boolean postPassQuestion =  questionClient.isPassQuestion(questionId, token, userId);
            // 这是看这个人做完了多少道题目的
            if(redisCache.getCacheObject(PASS_PROBLEM_MESSAGE + ":" + userId+ ":" + scoreType) == null){
                // 判断是否有没有创建这个字段。
                for (int i = 0; i < 3; i++) {
                    redisCache.setCacheObject(PASS_PROBLEM_MESSAGE + ":" + userId+ ":" + i, 0);
                }
            }
            if(postPassQuestion == false) redisCache.incrByCacheObject(PASS_PROBLEM_MESSAGE + ":" + userId + ":" + scoreType,  1);
        }
        redisCache.incrByCacheObject(TRY_PROBLEM_PERSON + ":" + scoreType, 1);
        redisCache.incrByCacheObject(TRY_PROBLEM_PERSON + ":" + userId + ":" + scoreType, 1);

        boolean updateOk = questionClient.updateSubmitRecordById(newSubmit, token);
        if(!updateOk){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新异常");
        }
        SubmitRecords recordSubmitById = questionClient.getRecordSubmitById(questionSubmitId, token);
        return recordSubmitById;
    }
}
