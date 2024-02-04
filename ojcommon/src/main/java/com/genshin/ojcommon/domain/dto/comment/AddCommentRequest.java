package com.genshin.ojcommon.domain.dto.comment;

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
 * @date 2024/1/9 8:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加评论请求")
public class AddCommentRequest implements Serializable {
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

    @ApiModelProperty(value = "Long", name = "文章id")
    private String articleId;

    @ApiModelProperty(value = "Long ", name = "顶级评论， 如果是null，代表该评论就是顶级评论")
    private String rootCommentId;

    @ApiModelProperty(value = "Long", name = "回复评论id")
    private String toCommentId;

    private Integer articleType;
}
