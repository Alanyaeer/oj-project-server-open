package com.genshin.ojcommon.domain.entity;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.genshin.ojcommon.domain.dto.article.AddArticleRequest;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojcommon.utils.JsonTransforUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2024/1/5 9:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "文章的实体类")
public class Article implements Serializable {
    @TableId
    // 记住必须要填入
    @ApiModelProperty(value = "Long", name = "文章的id")
    private Long id;

    @ApiModelProperty(value = "Long", name = "创建用户id")
    private Long userId;
    @ApiModelProperty(value = "String", name = "标题名称")
    private String titleName;

    @ApiModelProperty(value = "String", name = "标签列表")
    private String tags;
    @ApiModelProperty(value = "Integer", name = "点赞个数")

    private Integer thumbNum;
    @ApiModelProperty(value = "Integer", name = "收藏个数")

    private Integer favourNum;

    @ApiModelProperty(value = "LocalDateTime", name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @ApiModelProperty(value = "LocalDateTime", name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "Integer", name = "是否删除")
    private Integer isDelete;

    @ApiModelProperty(value = "String", name = "文章内容")
    private String content;

    @ApiModelProperty(value = "String", name = "文章描述（预览), 长度必须大于 等于 10 个字小于100个字")
    private String description;

    @ApiModelProperty(value = "Integer", name = "文章类别(0 表示 学习文章， 1表示题解文章)")
    private Integer articleType;
    private Integer articleReads;
    private Long articleId;
    // 或许还需要 评论功能， 但是 这里不知道放置在哪里( •̀ ω •́ )y
    public Article(AddArticleRequest request, Long userId){

        BeanUtils.copyProperties(request, this);
        this.setFavourNum(0);
        this.setThumbNum(0);
        this.setArticleReads(0);
        if(CollectionUtil.isEmpty(request.getTags()) == false)
            this.setTags(JsonTransforUtils.ArrayToJson(request.getTags()));
        else
            this.setTags("[]");
        this.setIsDelete(0);
        this.setUserId(GetLoginUserUtils.getUserId());
        this.setArticleId(Long.valueOf(request.getArticleId()));
    }
}
