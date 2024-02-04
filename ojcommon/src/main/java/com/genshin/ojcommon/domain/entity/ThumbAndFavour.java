package com.genshin.ojcommon.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 11:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "点赞收藏表")
public class ThumbAndFavour {
    @TableId
    @ApiModelProperty(value = "Long", name = "用户id")
    private Long userId;
    @ApiModelProperty(value = "Long", name = "来源id")
    private Long sourceId;
    @ApiModelProperty(value = "Integer ", name = "来源类型(0 表示 题目 1 表示 文章 2 表示评论）")

    private Integer type;
    @ApiModelProperty(value = "boolean", name = "是否点赞")
    private Boolean isThumb;
    @ApiModelProperty(value = "boolean", name = "是否收藏")
    private Boolean isFavour;

    public ThumbAndFavour(Long userId, String sourceId, Integer type, boolean isThumb, boolean isFavour){
        this.userId = userId;
        this.sourceId = Long.valueOf(sourceId);
        this.type = type;
        this.isThumb = isThumb;
        this.isFavour = isFavour;
    }

}
