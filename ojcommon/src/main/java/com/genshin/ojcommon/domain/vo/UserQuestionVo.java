package com.genshin.ojcommon.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/2 22:51
 */
@TableName(value = "user_question")
@ApiModel(description = "用户-问题类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestionVo implements Serializable {
    @ApiModelProperty(value = "Long", name = "用户id")
    private Long id;
    @ApiModelProperty(value = "int", name = "用户解决简单问题个数")
    private int solveEasy;
    @ApiModelProperty(value = "int", name = "用户解决中等问题个数")
    private int solveMiddle;
    @ApiModelProperty(value = "int", name = "用户解决困难问题个数")
    private int solveHard;
}
