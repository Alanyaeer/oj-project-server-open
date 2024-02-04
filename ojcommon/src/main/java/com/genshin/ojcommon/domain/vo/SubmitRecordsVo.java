package com.genshin.ojcommon.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2023/12/29 15:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "提交题目记录")
public class SubmitRecordsVo implements Serializable {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "Long", name = "题目提交编号")
    private Long id;
    @ApiModelProperty(value = "Long", name = "题目提交用户名")
    private Long pid;
    @ApiModelProperty(value = "Integer", name = "题目提交通过所有案例（0表示待判题， 1表示判题中， 2成功， 3失败）")

    private Integer status;

    @ApiModelProperty(value = "Long", name = "题目的id")
    private Long questionId;
    @ApiModelProperty(value = "String", name="判题信息")
    private JudgeInfo judgeInfo;

    @ApiModelProperty(value= "String", name ="语言")
    private String language;

    @ApiModelProperty(value = "String", name = "提交代码")
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "LocalDateTime", name = "题目提交更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "Long", name = "题目提交创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;



    @Deprecated
    @ApiModelProperty(value = "Long", name = "题目最后一个错误的案例数")
    private Integer lastCase;
    @Deprecated
    @ApiModelProperty(value = "Long", name = "题目最后一个错误的原因的映射（但是可以直接用HashMap 进行查询）")
    private Long lastReason;

    @ApiModelProperty(value = "String", name = "代码编译时期的错误")
    private String errorMessage;

    @ApiModelProperty(value = "String", name = "注释名称")
    public String annotation;
}
