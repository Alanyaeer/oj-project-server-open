package com.genshin.ojapi.client;

import com.genshin.ojcommon.domain.entity.SubmitRecords;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 吴嘉豪
 * @date 2024/1/12 9:53
 */
@FeignClient("oj-run")
public interface RunClient {
    @PostMapping("/runInner/runCode")
    SubmitRecords doJudge(@RequestParam("questionSubmitId") long questionSubmitId, @RequestParam("token") String token);
}
