package com.genshin.ojquestion.mapper;

import com.genshin.ojcommon.domain.entity.TagList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/20 14:46
 */
public interface TagListMapper {
    String getTagName(@Param("id") Long id);
    List<TagList> getAllTag();
}
