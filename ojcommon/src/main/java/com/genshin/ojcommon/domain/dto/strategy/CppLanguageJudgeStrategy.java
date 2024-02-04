package com.genshin.ojcommon.domain.dto.strategy;

import com.genshin.ojcommon.domain.dto.question.JudgeCase;
import com.genshin.ojcommon.domain.dto.question.JudgeConfig;
import com.genshin.ojcommon.domain.dto.question.JudgeContext;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.enums.JudgeInfoMessageEnum;
import com.genshin.ojcommon.domain.enums.QuestionSubmitStatusEnum;
import com.genshin.ojcommon.utils.JsonTransforUtils;
import java.util.List;
import java.util.Optional;

/**
 * @author 吴嘉豪
 * @date 2024/1/12 9:26
 */
public class CppLanguageJudgeStrategy implements JudgeStrategy{
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        Question question = judgeContext.getQuestion();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);
//        judgeContext.get
        judgeInfo.setMemory(memory);
        judgeInfo.setTime(time);
        JudgeInfoMessageEnum accepted = JudgeInfoMessageEnum.ACCEPTED;
        // 先进行 超时和内存判断， 因为时间超过一定范围之后， 我们的程序没有必要继续往后执行
        JudgeConfig judgeConfig = JsonTransforUtils.JsonToObj(JudgeConfig.class, question.getJudgeConfig());
        Long memoryLimit = judgeConfig.getMemoryLimit();
        Long timeLimit = judgeConfig.getTimeLimit();
        if(judgeContext.getJudgeInfo().getMessage().equals(JudgeInfoMessageEnum.COMPILE_ERROR.getText())){
            judgeInfo.setMessage(JudgeInfoMessageEnum.COMPILE_ERROR.getValue());
            return judgeInfo;
        }
//        if(judgeInfo.getMessage())
        if(memoryLimit < memory){
            JudgeInfoMessageEnum memoryLimitExceeded = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfo.setMessage(memoryLimitExceeded.getValue());
            return judgeInfo;
        }

        if(timeLimit  <= time){
            JudgeInfoMessageEnum timeLimitExceeded = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfo.setMessage(timeLimitExceeded.getValue());
            return judgeInfo;
        }
        if(outputList.size() != inputList.size()){
            JudgeInfoMessageEnum wrongAnswer = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfo.setMessage(wrongAnswer.getValue());
            return judgeInfo;
        }
        for(int i = 0; i < judgeCaseList.size(); ++i){
            if(!judgeCaseList.get(i).getOutput().equals(outputList.get(i))){
                JudgeInfoMessageEnum wrongAnswer = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfo.setMessage(wrongAnswer.getValue());
                return judgeInfo;
            }
        }

        judgeInfo.setMessage(accepted.getValue());
//        question.getJudgeConfig()
        return judgeInfo;
    }
}
