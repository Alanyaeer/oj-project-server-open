package com.genshin.ojcommon.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @date 2024/1/1 19:08
 */
@Data
@AllArgsConstructor
@Deprecated
@NoArgsConstructor
@ApiModel(description = "测试用例表")
public class Testcase implements Serializable {
    @TableId
    @ApiModelProperty(value = "id", name = "测试用例id")
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "LocalDateTime", name = "题目提交更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "LocalDateTime", name = "题目提交创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @ApiModelProperty(value = "type", name = "测试用例type")
    private Integer type;
    @ApiModelProperty(value = "TEXT", name = "题目输入用例TEXT")
    private String inputText;
    @ApiModelProperty(value = "TEXT", name = "题目输出用例TEXT")
    private String  outputText;
    @ApiModelProperty(value = "qid", name = "题目的id")
    private Long qid;
}
