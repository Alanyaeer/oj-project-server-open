package com.genshin.ojcommon.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.genshin.ojcommon.domain.entity.Article;
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
 * @date 2024/1/8 23:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "返回给前端的实体类")
public class ArticleListVo implements Serializable {

    @ApiModelProperty(value = "Long", name = "文章的id")
    private Long id;

    @ApiModelProperty(value = "Long", name = "创建用户id")
    private Long userId;

    @ApiModelProperty(value = "String", name = "标题名称")
    private String titleName;

    @ApiModelProperty(value = "Integer", name = "点赞个数")
    private Integer thumbNum;

    @ApiModelProperty(value = "Integer", name = "收藏个数")
    private Integer favourNum;

    @ApiModelProperty(value = "LocalDateTime", name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @ApiModelProperty(value = "String", name = "文章描述（预览), 长度必须大于 等于 10 个字小于100个字")
    private String description;

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

    public ArticleListVo(Article article) {
        BeanUtils.copyProperties(article,this);
    }
}
