package com.genshin.ojrun.controller.inner;

import com.genshin.ojapi.client.RunClient;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojrun.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴嘉豪
 * @date 2024/1/12 9:54
 */
@RestController
@RequestMapping("/runInner")
public class runInnerController implements RunClient {
    @Autowired
    private JudgeService judgeService;

    @Override
    @PostMapping("/runCode")
    public SubmitRecords doJudge(long questionSubmitId, String token) {
        SubmitRecords submitRecords =  judgeService.doJudge(questionSubmitId, token);
        return submitRecords;
    }
}
