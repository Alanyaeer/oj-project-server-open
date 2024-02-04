package com.genshin.ojuser.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.genshin.ojapi.client.ArticleClient;
import com.genshin.ojapi.client.QuestionClient;
import com.genshin.ojcommon.common.ErrorCode;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.constants.MqConstants;
import com.genshin.ojcommon.domain.dto.mq.MqDto;
import com.genshin.ojcommon.domain.entity.Comment;
import com.genshin.ojcommon.exception.BusinessException;
import com.genshin.ojuser.service.CommentService;
import com.genshin.ojuser.service.ThumbAndFavourService;
import com.genshin.ojuser.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 12:09
 */
@RestController
@RequestMapping("/thumbAndFavour")
public class ThumbAndFavourController {
    @Autowired
    private ThumbAndFavourService thumbAndFavourService;
    @Autowired
    private ArticleClient articleClient;
    @Autowired
    private QuestionClient questionClient;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 点赞 取消， 添加
     *
     *  0 代表 点赞 1 代表收藏
     *  0 代表 文章 1 代表题目 2 代表评论
     *  JSON 序列化 偶尔出现问题
     */

    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation("文章或题目点赞")
    @PostMapping("/thumbChange")
    @Transactional
    public ResponseResult addThumbNum(boolean isThumb, String sourceId, Integer type , HttpServletRequest request, Integer articleType){

        // 实际上这里还需要添加类型判断 如果是 文章类型 还需要进行区分 ？？
        Integer decrOrAdd = thumbAndFavourService.judgeDecrOrAdd(isThumb, 0,sourceId, type);

        boolean ThumbOk =  thumbAndFavourService.thumb(isThumb, sourceId, type);


        // 添加到 userThumb里面
        // 这个用户文章发布的用户信息进行修改
        Long ownerId = null;
//        private final static String QUESTION_EXCHANGE = "question.topic";
//        private final static String ARTICLE_EXCHANGE = "article.topic";
//
//        private final static  String  QUESTION_CF_QUEUE = "question.updateCf.topic";
//        private final static  String ARTICLE_CF_QUEUE = "article.updateCf.topic";
//        private final static  String  QUESTION_CF_KEY = "question.updateCf";
//        private final static  String ARTICLE_CF_KEY = "article.updateCf";
        if(type == 0){
            ownerId = articleClient.getUserIdByArticleId(Long.valueOf(sourceId), request.getHeader("token"));
            // 文章id 文章类型， decrOrAdd 增减 // 发送消息
            MqDto dto = new MqDto(Long.valueOf(sourceId), decrOrAdd, 0, articleType);
            rabbitTemplate.convertAndSend(MqConstants.ARTICLE_EXCHANGE, MqConstants.ARTICLE_CF_KEY, dto);
        }
        else if(type == 1){
            ownerId = questionClient.getUserIdByQuestionId(Long.valueOf(sourceId), request.getHeader("token"));
            // 问题id decrOrAdd 增减
            MqDto dto = new MqDto(Long.valueOf(sourceId), decrOrAdd, 0, articleType);
            rabbitTemplate.convertAndSend(MqConstants.QUESTION_EXCHANGE, MqConstants.QUESTION_CF_KEY, dto);
        }
        else {
            // 评论 的 点赞
            ownerId =  commentService.getUserIdByCommentId(sourceId);
            commentService.updateThumb(sourceId, decrOrAdd);

        }


        if(ownerId == null) return  new ResponseResult(205, "不存在该文章", 0);
        boolean updateOk =  userService.updateOwerId(ownerId, decrOrAdd, 0);
        // 放入消息队列 里面 告诉它去执行 ++ 操作

        if( updateOk == true)
            return new ResponseResult(200, "点赞完成", 1);
        else return  new ResponseResult(205, "点赞失败", 0);
    }
    /**
     * 收藏
     */
    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation("文章或题目收藏")
    @PostMapping("/favourChange")
    @Transactional
    public ResponseResult addFavourNum(boolean isFavour, String sourceId, Integer type, HttpServletRequest request, Integer articleType){
        Integer decrOrAdd = thumbAndFavourService.judgeDecrOrAdd(isFavour, 1,sourceId, type);
        boolean favourOk =  thumbAndFavourService.favour(isFavour, sourceId, type);


        // 添加到 userThumb里面
        Long ownerId = null;
        if(type == 0){
            ownerId = articleClient.getUserIdByArticleId(Long.valueOf(sourceId), request.getHeader("token"));
            MqDto dto = new MqDto(Long.valueOf(sourceId), decrOrAdd, 1, articleType);
            rabbitTemplate.convertAndSend(MqConstants.ARTICLE_EXCHANGE, MqConstants.ARTICLE_CF_KEY, dto);

        }
        else {
            ownerId = questionClient.getUserIdByQuestionId(Long.valueOf(sourceId), request.getHeader("token"));
            MqDto dto = new MqDto(Long.valueOf(sourceId), decrOrAdd, 1, articleType);
            rabbitTemplate.convertAndSend(MqConstants.QUESTION_EXCHANGE, MqConstants.QUESTION_CF_KEY, dto);

        }
        if(ownerId == null) throw  new BusinessException(ErrorCode.PARAMS_ERROR, "该用户不存在这个文章");
        // 点赞 还是 收藏
        boolean updateOk =  userService.updateOwerId(ownerId, decrOrAdd, 1);


        // 放入消息队列到文章 或者题目 里面 告诉它去执行 ++ 操作 //

        if( updateOk)
            return new ResponseResult(200, "收藏完成", 1);
        else  throw  new BusinessException(ErrorCode.PARAMS_ERROR, "失败");
    }
}
