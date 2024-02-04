package com.genshin.ojcommon.domain.dto.strategy;

import com.genshin.ojcommon.domain.dto.question.JudgeContext;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;

/**
 * @author 吴嘉豪
 * @date 2024/1/11 23:45
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
