package com.genshin.ojarticle.service.impl;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.genshin.ojapi.client.UserClient;
import com.genshin.ojapi.dto.vo.UserCardShowVo;
import com.genshin.ojarticle.mapper.ArticleMapper;
import com.genshin.ojarticle.service.ArticleService;
import com.genshin.ojcommon.domain.dto.article.AddArticleRequest;
import com.genshin.ojcommon.domain.entity.Article;
import com.genshin.ojcommon.domain.vo.ArticleContentVo;
import com.genshin.ojcommon.domain.vo.ArticleListVo;
import com.genshin.ojcommon.domain.vo.QuestionSolveVo;
import com.genshin.ojcommon.domain.vo.UserInfoVo;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojcommon.utils.JsonTransforUtils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 9:46
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserClient userClient;
    @Override
    public boolean addArticle(@RequestBody AddArticleRequest request) {
        Long userId = GetLoginUserUtils.getUserId();
        Article article = getArticleByAddRequest(request, userId);
        int insert = articleMapper.insert(article);
        return insert == 1 ? true : false;
    }

    private static Article getArticleByAddRequest(AddArticleRequest request, Long userId) {
        Article article = new Article(request, userId);
        return article;
    }

    @Override
    public boolean editArticle(AddArticleRequest request) {
        String articleId = String.valueOf(request.getArticleId());
        // 检验这个文章是不是本人的
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId, articleId);
        Long userId = GetLoginUserUtils.getUserId();
        wrapper.eq(Article::getUserId,userId);
        Integer i = articleMapper.selectCount(wrapper);
        if(i == 0) return false;
        Article article = getArticleByAddRequest(request, userId);
        articleMapper.updateById(article);
        return true;
    }

    @Override
    public boolean deleteArticle(String articleId) {
        Long userId = GetLoginUserUtils.getUserId();
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getUserId, userId);
        wrapper.eq(Article::getId, articleId);
//        Article article = articleMapper.selectOne(wrapper);
        Integer count = articleMapper.selectCount(wrapper);
        // 如果你找不到 ， 并且 你不是管理员
        if(count == 0 && !GetLoginUserUtils.isManager()){
            return false;
        }
        update().setSql("is_delete = 1").eq("id", articleId).update();
//        articleMapper.deleteById(articleId);
        return true ;
    }

    @Override
    public List<ArticleListVo> getList(int page, int pageSize, HttpServletRequest request) {
        Page<Article> articlePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getIsDelete, 0);
        wrapper.orderByDesc(Article::getUpdateTime);
        wrapper.select(
            Article::getId,
            Article::getUserId,
            Article::getTitleName,
            Article::getDescription,
            Article::getThumbNum,
            Article::getFavourNum,
            Article::getUpdateTime
        );
        page(articlePage, wrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleListVo> collect = records.stream().map(e->{
            ArticleListVo vo = new ArticleListVo();
            BeanUtils.copyProperties(e, vo);
            UserCardShowVo userCardShowVo = userClient.getUserCardInfoById(e.getUserId(), request.getHeader("token"));
            BeanUtils.copyProperties(userCardShowVo, vo);
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }
    private  UserCardShowVo getUserCard(Long userId, String token){
        return userClient.getUserCardInfoById(userId, token);
    }
    @Override
    public ArticleContentVo getArticleContentById(String articleId, HttpServletRequest request) {
        Article article = getById(articleId);
        ArticleContentVo contentVo = new ArticleContentVo();
        BeanUtils.copyProperties(article, contentVo);
        UserCardShowVo userCard = getUserCard(article.getUserId(), request.getHeader("token"));
//        UserCardShowVo userCardShowVo = userClient.getUserCardInfoById(e.getUserId(), request.getHeader("token"));
        BeanUtils.copyProperties(userCard, contentVo);

        return contentVo;

    }

    @Override
    public List<QuestionSolveVo> getQuestionSolveByQuestionId(String questionId, int page, int pageSize, String token) {
        Page<Article> articlePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getArticleId, questionId);
        wrapper.eq(Article::getArticleType, 1);
        wrapper.eq(Article::getIsDelete, 0);
        articleMapper.selectPage(articlePage, wrapper);
        List<Article> records = articlePage.getRecords();
        List<QuestionSolveVo> questionSolveVoList = records.stream().map(e -> {
            QuestionSolveVo questionSolveVo = new QuestionSolveVo();
            BeanUtils.copyProperties(e, questionSolveVo);
            List<String> tagList = JsonTransforUtils.JsonToArray(String.class, e.getTags());
            if(CollectionUtils.isEmpty(tagList)) tagList = new ArrayList<>();
            questionSolveVo.setTags(tagList);
            Integer commentNum =  userClient.getCommentNumByIdType(questionId, 1, token);
            questionSolveVo.setArticleTalks(commentNum);
            questionSolveVo.setArticleReads(e.getArticleReads());
            questionSolveVo.setId(e.getId());
            questionSolveVo.setArticleThumbNums(e.getThumbNum());
            UserInfoVo userInfoVo =  userClient.getUserInfo(e.getUserId(), token);
            BeanUtils.copyProperties(userInfoVo, questionSolveVo);
            questionSolveVo.setId(e.getId());
            questionSolveVo.setDateTime(e.getUpdateTime());
            return questionSolveVo;
        }).collect(Collectors.toList());
        return questionSolveVoList;
    }
}
