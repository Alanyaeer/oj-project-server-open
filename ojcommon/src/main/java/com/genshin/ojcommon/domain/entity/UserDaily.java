package com.genshin.ojcommon.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2024/1/3 22:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户日常的记录")
public class UserDaily implements Serializable {
    @ApiModelProperty(value = "Long", name = "记录id")
    private Long id;
//    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
//    @ApiModelProperty(value = "LocalDateTime", name = "题目提交更新时间")
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "LocalDateTime", name = "创造时间")
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "LocalDateTime", name = "更新时间")
    private LocalDateTime updateTime;
    private Integer times;
}
