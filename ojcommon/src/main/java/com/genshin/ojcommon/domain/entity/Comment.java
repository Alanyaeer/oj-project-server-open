package com.genshin.ojcommon.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2024/1/6 22:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "评论实体类")
public class Comment implements Serializable {
    @ApiModelProperty(value = "Long", name = "评论id")
    @TableId
    private Long id;
    @ApiModelProperty(value = "String", name = "评论内容")

    private String content;

    @ApiModelProperty(value = "LocalDateTime", name = "评论创造时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @ApiModelProperty(value = "LocalDateTime", name = "评论更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "Integer", name = "是否删除")

    private Integer isDelete;

    @ApiModelProperty(value = "String", name = "用户昵称")

    private Long userId;
    @ApiModelProperty(value = "Long", name = "文章id")

    private Long articleId;
    @ApiModelProperty(value = "Integer", name = "评论点赞个数")

    private Integer commentLikeCount;
    @ApiModelProperty(value = "Long ", name = "顶级评论， 如果是null，代表该评论就是顶级评论")

    private Long rootCommentId;
    @ApiModelProperty(value = "Long", name = "回复评论id")

    private Long toCommentId;

    @ApiModelProperty(value = "Integer", name = "文章类型")
    private Integer articleType;

}
