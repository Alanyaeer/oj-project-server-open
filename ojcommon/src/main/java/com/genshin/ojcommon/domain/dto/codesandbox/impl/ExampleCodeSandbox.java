package com.genshin.ojcommon.domain.dto.codesandbox.impl;

import com.genshin.ojcommon.domain.dto.codesandbox.CodeSandbox;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeRequest;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeResponse;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.enums.JudgeInfoMessageEnum;
import com.genshin.ojcommon.domain.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/11 23:40
 */
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        // 输出结果， 输出题目提交的状态 ， 输出判题信息， 判题消耗时间 ， 判题消耗空间
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
