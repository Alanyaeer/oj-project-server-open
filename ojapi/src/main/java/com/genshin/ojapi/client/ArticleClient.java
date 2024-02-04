package com.genshin.ojapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 21:22
 */
@FeignClient("oj-article")
public interface ArticleClient {

    @PostMapping("/articleInner/getUserIdByArticleId")
    Long getUserIdByArticleId(@RequestParam("sourceId") Long articleId, @RequestHeader("token") String token);
}
