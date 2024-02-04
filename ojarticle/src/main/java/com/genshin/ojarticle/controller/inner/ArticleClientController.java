package com.genshin.ojarticle.controller.inner;

import com.genshin.ojapi.client.ArticleClient;
import com.genshin.ojarticle.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 21:21
 */
@RestController
@RequestMapping("/articleInner")
public class ArticleClientController implements ArticleClient {
    @Autowired
    private ArticleService articleService;

    // 通过文章id 来返回用户的 id
    @PostMapping("/getUserIdByArticleId")
    public Long getUserIdByArticleId(Long articleId, String token){
        Long id = articleService.getById(articleId).getUserId();
        return id;
    }
//
//    /**
//     * 收藏
//     */
//    @PostMapping("/isFavour")
//    public void
}
