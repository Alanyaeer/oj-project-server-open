package com.genshin.ojcommon.domain.vo;

import com.genshin.ojcommon.domain.entity.Category;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 10:16
 */
@Data
public class CategoryVo implements Serializable {
    public Integer value;
    public String label;
    public CategoryVo(Category category){
        this.value = category.getId().intValue();
        this.label = category.getCategory();
    }
}
