package com.genshin.ojarticle.controller;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.genshin.ojapi.client.UserClient;
import com.genshin.ojarticle.mapper.CategoryMapper;
import com.genshin.ojarticle.service.ArticleService;
import com.genshin.ojcommon.common.ErrorCode;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.dto.article.AddArticleRequest;
import com.genshin.ojcommon.domain.entity.Article;
import com.genshin.ojcommon.domain.entity.Category;
import com.genshin.ojcommon.domain.entity.UserConnection;
import com.genshin.ojcommon.domain.vo.*;
import com.genshin.ojcommon.exception.BusinessException;
import com.genshin.ojcommon.utils.JsonTransforUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 9:08
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserClient userClient;
    @PostMapping("/queryCategories")
    public ResponseResult queryCategories(){
        List<Category> list = categoryMapper.getCategoryList();
        List<CategoryVo> collect = list.stream().map(CategoryVo::new).collect(Collectors.toList());
        return new ResponseResult(200, "获取前端list", collect);
    }

    @PostMapping("/fortest")
    public ResponseResult test(){
        return new ResponseResult(200, "hudaf", 1);
    }
    /**
     * 添加文章
     */
//    @PreAuthorize("hasAuthority('vip')")
//    @PostMapping("/addArticle")
//    @ApiOperation("添加文章")
//    public ResponseResult addArticle(@RequestBody AddArticleRequest request){
//        if(request.getContent().length() < 10 || request.getContent().length() > 100)return new ResponseResult(205, "添加文章失败，介绍过长", 0);
//        articleService.addArticle(request);
//        return new ResponseResult(200, "添加文章成功", 1);
//    }

    /**
     * 编辑文章
     */
    @PreAuthorize("hasAuthority('vip')")
    @PostMapping("/editArticle")
    @ApiOperation("编辑文章")
    public ResponseResult editArticle(@RequestBody AddArticleRequest request){
        if(request.getContent().length() < 10 || request.getContent().length() > 100)return new ResponseResult(205, "添加文章失败，介绍过长", 0);

        boolean editOk =  articleService.editArticle(request);
        if(editOk)
        return new ResponseResult(200, "编辑文章成功 ", 1);
        else
            return new ResponseResult(205, "编辑文章失败， 数据存在篡改", 0);
    }
    /**
     * 删除文章
     */
    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation("删除文章")
    @PostMapping("/deleteArticle")
    public ResponseResult deleteArticle(String articleId){
//        if(articleId == null) throw new BusinessException(ErrorCode.NULL_ERROR);
        boolean deleteOk =  articleService.deleteArticle(articleId);
        if(deleteOk)
        return new ResponseResult<>(200, "删除成功", 1);
        else return new ResponseResult<>(205, "删除失败", 0);
    }



    /**
     * 获取文章 列表（只获取文章列表， 不带有内容）
     */
    @PostMapping("/getArticleList")
    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation("获取文章列表")
    public ResponseResult getArticleList(int page, int pageSize, HttpServletRequest request){
        List<ArticleListVo> articleList =  articleService.getList(page, pageSize,  request);
        return new ResponseResult(200, "获取到文章列表", articleList);
    }
    /**
     * 获取文章内容 （包括文章的详细内容）
     *
     */
//    @PostMapping("/getArticleContent")
//    @ApiOperation("获取文章内容")
//    @PreAuthorize("hasAuthority('vip')")
//    public ResponseResult getArticleContent(@RequestParam("articleId") String articleId,HttpServletRequest request){
//        ArticleContentVo article = articleService.getArticleContentById(articleId,request);
//        return  new ResponseResult<>(200, "获取文章内容成功", article);
//
//    }

    /**
     * pageSize 15
     * // 获取用户信息
     * @param request
     * @param questionId
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/getQuestionSolveArticleList")
    @ApiOperation("获取题目的列表")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getQuestionSolveArticleList (HttpServletRequest request, String questionId, int page, int pageSize){
        String token = request.getHeader("token");
        List<QuestionSolveVo> questionSolveVoList =  articleService.getQuestionSolveByQuestionId(questionId, page, pageSize ,token);
        return new ResponseResult(200, "获取列表", questionSolveVoList);
    }
    @PostMapping("/getArticleContent")
    @ApiOperation("获取文章内容")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getArticleContentById (HttpServletRequest request, String id){
        Article article = articleService.getById(id);
        Long userId = article.getUserId();
        UserInfoVo userInfo = userClient.getUserInfo(Long.valueOf(userId), request.getHeader("token"));
        ArticleShowContentVo contentVo = new ArticleShowContentVo();
        contentVo.setUserInfo(userInfo);
        BeanUtils.copyProperties(article, contentVo);
        contentVo.setTags(JsonTransforUtils.JsonToArray(String.class, article.getTags()));
        return new ResponseResult(200, "获取文章内容", contentVo);
    }
    @PostMapping("/addArticle")
    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation("添加文章内容")
    public ResponseResult addArticle(@RequestBody AddArticleRequest request){
        boolean b = articleService.addArticle(request);
        return new ResponseResult(200,"添加成功", b);
    }
}
