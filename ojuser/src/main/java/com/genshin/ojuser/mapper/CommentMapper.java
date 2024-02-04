package com.genshin.ojuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.genshin.ojcommon.domain.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 9:47
 */
public interface CommentMapper extends BaseMapper<Comment> {
    void deleteComment( Long rootCommentId);
//    int i = commentMapper.queryCommentNumByIdType(Long.valueOf(questionId), questionType);

    int queryCommentNumByIdType(@Param("articleId") Long articleId,@Param("articleType") int articleType);
    List<Comment>  queryRootCommentList(@Param("articleId") Long articleId,@Param("offset") int offset,@Param("pageSize") int pageSize,@Param("articleType") int articleType);

    List<Comment> querySonCommentList(@Param("rootId") Long rootId,@Param("offset") int offset,@Param("pageSize") int pageSize,@Param("articleType") int articleType);

    List<Comment> queryTopThirdCommentList(@Param("rootId") Long rootId,@Param("articleType") int articleType);
}
