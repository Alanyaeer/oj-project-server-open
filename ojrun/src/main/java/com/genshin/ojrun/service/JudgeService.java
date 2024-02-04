package com.genshin.ojrun.service;

import com.genshin.ojcommon.domain.entity.SubmitRecords;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 吴嘉豪
 * @date 2024/1/12 10:29
 */

public interface JudgeService {
    SubmitRecords doJudge(long questionSubmitId, String token);
}
