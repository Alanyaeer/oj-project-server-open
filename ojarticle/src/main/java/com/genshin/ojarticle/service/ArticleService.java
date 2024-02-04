package com.genshin.ojarticle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.domain.dto.article.AddArticleRequest;
import com.genshin.ojcommon.domain.entity.Article;
import com.genshin.ojcommon.domain.vo.ArticleContentVo;
import com.genshin.ojcommon.domain.vo.ArticleListVo;
import com.genshin.ojcommon.domain.vo.QuestionSolveVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 9:46
 */
public interface ArticleService extends IService<Article> {
    boolean addArticle(AddArticleRequest request);

    boolean editArticle(AddArticleRequest request);

    boolean deleteArticle(String articleId);

    List<ArticleListVo> getList(int page, int pageSize, HttpServletRequest request);

    ArticleContentVo getArticleContentById(String articleId,HttpServletRequest request);

    List<QuestionSolveVo> getQuestionSolveByQuestionId(String questionId, int page, int pageSize, String token);
}
