package com.genshin.ojuser.controller;

import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.dto.comment.AddCommentRequest;
import com.genshin.ojcommon.domain.entity.Comment;
import com.genshin.ojcommon.domain.vo.CommentVo;
import com.genshin.ojcommon.domain.vo.UserInfoVo;
import com.genshin.ojuser.mapper.CommentMapper;
import com.genshin.ojuser.service.CommentService;
import com.genshin.ojuser.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
*@author 吴嘉豪
*@date 2024/1/7 9:13
*/
@RestController
@RequestMapping("/comment")
@Slf4j
public  class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentMapper commentMapper;



    @PostMapping("/queryCommentImmediate")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult queryCommentImmediate(String id){
        Comment comment = commentService.getById(id);
        List<Comment> list = new ArrayList<>();
        list.add(comment);
        List<CommentVo> commentVos = getCommentVos(list);
        return new ResponseResult(200, "获取刚刚发送的消息",commentVos.get(0));
    }
    /**
     *  添加评论
     *  最顶层的评论
     *  不是最顶层的评论 isTopTopic
     */

    @ApiOperation("isTopTopic")
    @PostMapping("/addComment")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult addComment(@RequestBody AddCommentRequest request){
        Long commentId =  commentService.addComment(request);
        return new ResponseResult(200, "添加评论 ", commentId.toString());
    }


    /**
     *  删除评论
     */
    @ApiOperation("deleteTopic")
    @PostMapping("/deleteTopic")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult deleteComment(String commentId){
        commentService.deleteById(commentId);
        return new ResponseResult(200, "删除评论", 1);
    }
    /**
     *  获取一个所有父级评论 - 分页查询
     */

    @ApiOperation("获取前十条评论评论")
    @PostMapping("/getCommentList")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getThirdComment( String articleId, int page, int pageSize, int articleType){
        List<Comment> commentList =  commentService.queryPage(articleId, page, pageSize, articleType);
        List<CommentVo> collect = getCommentVos(commentList);
        return new ResponseResult<>(200, "获取评论成功", collect);
    }

    private List<CommentVo> getCommentVos(List<Comment> commentList) {
        List<CommentVo> collect = commentList.stream().map(e -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(e, commentVo);
            Long id = e.getUserId();
            UserInfoVo userInfoVo = userService.queryUserInfo(id);
            // 用户信息的部分
            commentVo.setUserInfo(userInfoVo);
            commentVo.setUpdateTime(e.getUpdateTime());
            return commentVo;
        }).collect(Collectors.toList());
        return collect;
    }

    @ApiOperation("获取每一个评论的子评论， 默认先获取三条，如果点击展开就分页查询")
    @PostMapping("/getSonComment")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getThreeSonComment(@RequestParam("rootId") String rootId, int page, int pageSize, Boolean isExtension, Integer articleType){
        List<Comment> list = null;
        if(isExtension == true){
//            commentService.query
             list = commentMapper.querySonCommentList(Long.valueOf(rootId), (page - 1) * pageSize, pageSize, articleType);
        }
        else{
            list = commentMapper.queryTopThirdCommentList(Long.valueOf(rootId), articleType);
        }
        List<CommentVo> commentVos = getCommentVos(list);
        return new ResponseResult(200, "获取成功", commentVos);
    }


    // 点赞  。 没有收藏功能 这个功能 直接合并到 ThumbAndFavourController 里面

//    @ApiOperation("给评论点赞")
//    @PostMapping("/addThumbToComment")
//    @PreAuthorize("hasAuthority('vip')")
//    public ResponseResult addThumbToComment(String commentId, Boolean commentStatus){
////        int add =
//        // 用户点赞状态变为  commentStatus
//
//        // 用户个人点赞个数增加
//
//        return null;
//
//    }
}
