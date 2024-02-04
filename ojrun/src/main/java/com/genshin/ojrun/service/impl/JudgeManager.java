package com.genshin.ojrun.service.impl;
import com.genshin.ojcommon.domain.dto.question.JudgeContext;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.dto.strategy.*;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        SubmitRecords questionSubmit = judgeContext.getSubmitRecords();
        String language = questionSubmit.getLanguage();
//        CPP(0, "CPP"),
//                JAVA(1, "JAVA"),
//                GO(2, "GO");
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("JAVA".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        else if ("CPP".equals(language)){
            judgeStrategy = new CppLanguageJudgeStrategy();
        }
        else  if("GO".equals(language)){
            judgeStrategy = new GoLanguageJudgeStrategy();
        }
        else if("C".equals(language)){
            judgeStrategy = new CLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
