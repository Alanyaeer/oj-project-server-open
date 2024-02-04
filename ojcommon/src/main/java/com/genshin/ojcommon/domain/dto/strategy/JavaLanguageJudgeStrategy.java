package com.genshin.ojcommon.domain.dto.strategy;

import cn.hutool.json.JSONUtil;
import com.genshin.ojcommon.domain.dto.question.JudgeCase;
import com.genshin.ojcommon.domain.dto.question.JudgeConfig;
import com.genshin.ojcommon.domain.dto.question.JudgeContext;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.enums.JudgeInfoMessageEnum;
import com.genshin.ojcommon.utils.JsonTransforUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author 吴嘉豪
 * @date 2024/1/11 23:46
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy{
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        // 如果为 null 就返回 null ， 否则就是 0
        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;

        JudgeInfo infoRep = new JudgeInfo();
        infoRep.setMemory(memory);
        infoRep.setTime(time);
        String judgeConfigStr = question.getJudgeConfig();
//        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        JudgeConfig judgeConfig = JsonTransforUtils.JsonToObj(JudgeConfig.class, judgeConfigStr);
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        Long needTimeLimit = judgeConfig.getTimeLimit();
        if(judgeContext.getJudgeInfo().getMessage().equals(JudgeInfoMessageEnum.COMPILE_ERROR.getText())){
            judgeInfo.setMessage(JudgeInfoMessageEnum.COMPILE_ERROR.getValue());
            return judgeInfo;
        }
        if (memory > needMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            infoRep.setMessage(judgeInfoMessageEnum.getValue());
            return infoRep;
        }
        // Java 程序本身需要额外执行 10 秒钟
        long JAVA_PROGRAM_TIME_COST = 10000L;
        if ((time - JAVA_PROGRAM_TIME_COST) > needTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            infoRep.setMessage(judgeInfoMessageEnum.getValue());
            return infoRep;
        }
        // 判断 执行结果 和 执行过程是否一样
        if(inputList.size()  != outputList.size()){
            JudgeInfoMessageEnum wrongAnswer = JudgeInfoMessageEnum.WRONG_ANSWER;
            infoRep.setMessage(wrongAnswer.getValue());
            return infoRep;
        }
        for(int i = 0; i < judgeCaseList.size(); ++i){
            if(outputList.get(i).equals(judgeCaseList.get(i).getOutput()) == false ){
                JudgeInfoMessageEnum wrongAnswer = JudgeInfoMessageEnum.WRONG_ANSWER;
                infoRep.setMessage(wrongAnswer.getValue());
                return infoRep;
            }
        }
        // 判断题目限制

        infoRep.setMessage(judgeInfoMessageEnum.getValue());
        return infoRep;
    }
}
