package com.genshin.ojcommon.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2023/12/10 14:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "题目实体类")
@TableName(value="question")
public class Question implements Serializable {
    @ApiModelProperty(value = "题目id")
    @TableId
    private Long id;

    @ApiModelProperty(value = "题目名称")
    private String titleName;

    @ApiModelProperty(value = "题目创建者")

    private String createBy;

    @ApiModelProperty(value = "题目更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "题目创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @Deprecated
    @ApiModelProperty(value = "题目文件创建地址")
    private String questionLocation;

    @ApiModelProperty(value = "0表示正常状态， 1表示请求删除，2表示已被删除，3表示请求审核题目")
    private Integer questionType;

    @ApiModelProperty( value = "点赞个数")
    private Integer likes;


    @ApiModelProperty(value = "通过人数")
    private Integer passPerson;

    @ApiModelProperty(value = "难度分数")
    private Integer score;

    @Deprecated
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "判断题目的测试用例") // 注意这里到底是json 还是 String 类型想清楚
    private String judgeCase;

    @ApiModelProperty(value = "收藏个数")
    private Integer favourNum;


    @ApiModelProperty(value = "提交人数")
    private Integer submitNum;

    @Deprecated
    @ApiModelProperty(value = "题目答案")
    private String ans;

    @ApiModelProperty(value = "标签列表")
    private String tags;

    @ApiModelProperty(value = "题目内容")
    private String content;
    @ApiModelProperty(value = "创建者的id")
    private Long createUserId;

    @ApiModelProperty(value = "题目的回显id")
    private Integer titleId;

    @ApiModelProperty(value = "题目的限制")
    private String judgeConfig;

    @ApiModelProperty(value = "String")
    private String language;
}
