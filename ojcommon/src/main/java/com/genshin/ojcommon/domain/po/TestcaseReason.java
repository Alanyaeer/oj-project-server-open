package com.genshin.ojcommon.domain.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2023/12/29 15:49
 */
@Data
@NoArgsConstructor
@Deprecated
@AllArgsConstructor
@ApiModel(description = "测试用例错误可能")
public class TestcaseReason implements Serializable {
    @ApiModelProperty(value = "Long", name = "题目提交编号")
    private Long id;
    @ApiModelProperty(value = "String", name = "题目错误原因")
    private String reason;
}
