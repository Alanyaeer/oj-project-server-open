package com.genshin.ojuser.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.genshin.ojcommon.common.ErrorCode;
import com.genshin.ojcommon.domain.dto.comment.AddCommentRequest;
import com.genshin.ojcommon.domain.entity.Comment;
import com.genshin.ojcommon.exception.BusinessException;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojuser.mapper.CommentMapper;
import com.genshin.ojuser.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 9:46
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    private Comment initCommentProcessor(Comment comment, AddCommentRequest request){
        BeanUtils.copyProperties(request, comment);
        comment.setCommentLikeCount(0);
        comment.setIsDelete(0);
        Long userId = GetLoginUserUtils.getUserId();
        comment.setArticleId(Long.valueOf(request.getArticleId()));
        comment.setUserId(userId);
        if(StrUtil.isEmpty(request.getToCommentId()) == false)
        comment.setToCommentId(Long.valueOf(request.getToCommentId()));
        if(StrUtil.isEmpty(request.getRootCommentId())==false)
        comment.setRootCommentId(Long.valueOf(request.getRootCommentId()));
        return comment;
    }
    @Override
    public Long addComment(AddCommentRequest request) {
        Comment comment = new Comment();
        Comment commentAfterInit = initCommentProcessor(comment, request);
        save(commentAfterInit);
        return commentAfterInit.getId() ;
    }

    @Override
    public void deleteById(String commentId) {
        // 先删除自己然后 在校验
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 构造器
        queryWrapper.eq(Comment::getId, commentId);
        queryWrapper.eq(Comment::getUserId, GetLoginUserUtils.getUserId());
        queryWrapper.select(Comment::getRootCommentId);
        Comment comment = commentMapper.selectOne(queryWrapper);
        if(comment == null) throw new BusinessException(ErrorCode.NULL_ERROR);
        Long rootCommentId = comment.getRootCommentId();
        update().setSql("is_delete = 1").eq("id", commentId).update();
//        commentMapper.updateById(comment);
        if(rootCommentId == null){
            // 如果是顶层评论
            commentMapper.deleteComment(rootCommentId);
        }

    }

    @Override
    public List<Comment> queryPage(String articleId, int page, int pageSize, int articleType) {
        int offset = (page - 1) * pageSize;
        List<Comment> list = commentMapper.queryRootCommentList(Long.valueOf(articleId), offset, pageSize, articleType);
        return list;
    }

    @Override
    public Long getUserIdByCommentId(String sourceId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getId, sourceId);
//            queryWrapper
        queryWrapper.select(Comment::getUserId);
        Comment comment = commentMapper.selectOne(queryWrapper);
        return comment.getUserId();
    }

    @Override
    public void updateThumb(String sourceId, Integer decrOrAdd) {

        if(decrOrAdd == -1){
            update().setSql("comment_like_count = 0").eq("source_id", sourceId).update();
        }
        else if(decrOrAdd == 1){
            update().setSql("comment_like_count = 1").eq("source_id", sourceId).update();

        }
//        commentMapper.u
//        commentMapper.

    }
}
