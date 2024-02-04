package com.genshin.ojcommon.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2024/1/2 22:54
 */
@Data
@TableName("user_solve")
@ApiModel(description = "用户解决类")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Deprecated
public class UserSolve {
    @ApiModelProperty(value = "Long", name = "用户id")
    private Long id;

    @ApiModelProperty(value = "LocalDateTime", name = "用户解决该题目更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "LocalDateTime", name = "用户解决该题目创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "Long", name = "题目编号id")
    private Long titleId;

}
