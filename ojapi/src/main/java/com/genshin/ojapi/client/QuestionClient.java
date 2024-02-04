package com.genshin.ojapi.client;

import com.genshin.ojapi.dto.CaseApiDto;
import com.genshin.ojapi.dto.SubmitDto;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/30 18:04
 */

@FeignClient("oj-question")
public interface QuestionClient {
    @PutMapping(value = "/getQuestion/insert")
    void insert(@RequestBody SubmitDto submitDto, @RequestHeader("token") String token);

    @PostMapping(value = "/getQuestion/getTestcaseInPutFromMachine")
    List<CaseApiDto> getTextCase(@RequestParam("titleId") String titleId, @RequestHeader("token") String token);

    @PostMapping(value = "/problem/addQuestionRec")
    void RecordQuestion(@RequestParam("questionId") Long titleId,@RequestParam("userId") Long userId, @RequestHeader("token") String token);

    @PostMapping(value = "/problemInner/getUserIdByQuestionId")
    Long getUserIdByQuestionId(@RequestParam("sourceId") Long questionId, @RequestHeader("token") String token);

    @PostMapping(value = "/problemInner/getQuestionSubmitById")
    SubmitRecords getRecordSubmitById(@RequestParam("questionSubmitId") long questionSubmitId, @RequestHeader("token") String token);

    @PostMapping(value = "/problemInner/getQuestionById")
    Question getQuestionById(@RequestParam("questionId") Long questionId, @RequestHeader("token") String token);
    @PostMapping(value = "/problemInner/updateSubmitRecordsById")
    boolean updateSubmitRecordById(@RequestBody SubmitRecords submit,@RequestHeader("token") String token);
    @PostMapping(value = "/problemInner/updateQuestion")
    boolean updateQuestion(@RequestBody Question  question, @RequestHeader("token") String token);
    @PostMapping(value = "/problemInner/passQuestionOk")
    boolean isPassQuestion(@RequestParam("questionId") Long questionId, @RequestHeader("token") String token, @RequestParam("userId") Long userId);
}
