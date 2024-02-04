package com.genshin.ojquestion.controller.inner;

import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojquestion.service.QuestionService;
import com.genshin.ojquestion.service.SubmitRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 23:54
 */
@RestController
@RequestMapping("/problemInner")
public class ProblemClientController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private SubmitRecordsService submitRecordsService;
    @PostMapping("/getUserIdByQuestionId")
    public Long getUserIdByQuestionId(Long sourceId, String token){
        Question question = questionService.getById(sourceId);
        return question.getCreateUserId();
    }
    @PostMapping("/getQuestionSubmitById")
    SubmitRecords getRecordSubmitById(long questionSubmitId, String token){
        SubmitRecords submitRecords = submitRecordsService.getById(questionSubmitId);
        return submitRecords;
    }
    @PostMapping("/getQuestionById")
    Question getQuestionById(long questionId, String token){
        Question question = questionService.getById(questionId);
        return question;
    }
    @PostMapping("/updateSubmitRecordsById")
    boolean updateSubmitRecordById(@RequestBody(required = false) SubmitRecords submit, String token){
        boolean updateOk = submitRecordsService.updateById(submit);
        return updateOk;
    }

    @PostMapping("/updateQuestion")
    boolean updateQuestion( @RequestBody Question  question, @RequestHeader("token") String token){
        boolean updateOk = questionService.updateById(question);
        return updateOk;
    }
    @PostMapping(value = "/passQuestionOk")
    boolean isPassPerson(Long questionId, String token, Long userId){
        return  questionService.isPassThisQuestionByUserId(questionId, userId);
    }
//    @PostMapping()
//    @PostMapping(value = "/problemInner/getQuestionSubmitById")
//    SubmitRecords getRecordSubmitById(@RequestParam("questionSubmitId") long questionSubmitId, @RequestHeader("token") String token);

//    @PostMapping(value = "/problemInner/getQuestionById")
//    Question getQuestionById(@RequestParam("questionId") Long questionId, @RequestHeader("token") String token);
//    @PostMapping(value = "/problemInner/updateSubmitRecordsById")
//    boolean updateSubmitRecordById(@RequestParam("submit") SubmitRecords submit,@RequestHeader("token") String token);
}
