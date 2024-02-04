package com.genshin.ojcommon.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.genshin.ojcommon.domain.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2023/12/11 13:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "algo")
@ApiModel(value = "算法模板实体类")
@Deprecated
public class Algo extends Article {

    @ApiModelProperty(name = "算法模板id", value = "id")
    private Long id;

    @ApiModelProperty(name = "创建者", value = "createBy")
    private String createBy;

    @ApiModelProperty(name = "算法模板创建时间", value = "updateTime")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(name = "算法模板创建时间", value = "createTime")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "算法模板标签", value = "String")
    private String tags;

    @ApiModelProperty(name = "算法模板内容", value = "String")
    private String content;

//    @ApiModelProperty(name = "算法模板标题", value = "")
}
