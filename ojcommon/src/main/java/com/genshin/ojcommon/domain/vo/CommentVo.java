package com.genshin.ojcommon.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
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
 * @date 2024/1/9 9:03
 */
@ApiModel(description = "返回给前端的评论类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo implements Serializable {
    @ApiModelProperty(value = "Long", name = "评论id")
    @TableId(type = IdType.AUTO)
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "String", name = "评论内容")
    private String content;

    @ApiModelProperty(value = "LocalDateTime", name = "评论创造时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "Integer", name = "评论点赞个数")
    private Integer commentLikeCount;

    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty(value = "Long ", name = "顶级评论， 如果是null，代表该评论就是顶级评论")
    private Long rootCommentId;

    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty(value = "Long", name = "回复评论id")
    private Long toCommentId;

    private UserInfoVo userInfo;
    @ApiModelProperty(value = "String", name = "用户头像")
    private String avatar;

    //    private Integer User
    @ApiModelProperty(value = "Integer", name = "用户收藏人数")
    private Integer UserFavourNum;
    @ApiModelProperty(value = "Integer", name = "用户关注人数")

    private Integer Followers;
    @ApiModelProperty(value = "Integer", name = "用户点赞人数")

    private Integer UserThumbNum;
        @ApiModelProperty(value = "String", name = "用户昵称")
    private String NickName;
    @ApiModelProperty(value = "String", name = "用户描述")

    private String UserDescription;

    @ApiModelProperty(value = "String", name = "是否关注该用户")
    private Boolean isFollow;
}
