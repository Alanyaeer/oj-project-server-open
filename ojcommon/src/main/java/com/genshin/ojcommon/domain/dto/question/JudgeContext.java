package com.genshin.ojcommon.domain.dto.question;

import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;
    // 提交记录 类似与 questionSubmits
    private SubmitRecords submitRecords;

}