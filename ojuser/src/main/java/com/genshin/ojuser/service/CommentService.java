package com.genshin.ojuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.domain.dto.comment.AddCommentRequest;
import com.genshin.ojcommon.domain.entity.Comment;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 9:46
 */
public interface CommentService extends IService<Comment> {
    Long addComment(AddCommentRequest request);

    void deleteById(String commentId);

    List<Comment> queryPage(String articleId, int page, int pageSize, int articleType);

    Long getUserIdByCommentId(String sourceId);

    void updateThumb(String sourceId, Integer decrOrAdd);
}
