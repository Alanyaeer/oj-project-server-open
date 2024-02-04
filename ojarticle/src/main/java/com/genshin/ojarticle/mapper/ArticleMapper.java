package com.genshin.ojarticle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.genshin.ojcommon.domain.entity.Article;

/**
 * @author 吴嘉豪
 * @date 2024/1/6 23:38
 */
public interface ArticleMapper extends BaseMapper<Article> {
//    # 根据时间排序
//    SELECT * FROM comment ct where
//            (select count(*) from comment where root_comment_id = ct.root_comment_id and id <= ct.id) <= 3
//    and ct.root_comment_id = 0 order by root_comment_id , id DESC
//
//      # 根据点赞个数排序， 然后在根据 时间排序
//
//    SELECT * FROM comment ct where
//    ct.root_comment_id = 0 order by root_comment_id DESC,  comment_like_count DESC
}
