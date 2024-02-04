package com.genshin.ojcommon.domain.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 吴嘉豪
 * @date 2023/12/11 13:49
 */
@Data
@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "算法模板名称列表")
public class AlgTemList {
    @ApiModelProperty(name = "算法模板名称id", value = "id")

    private Long id;
    @ApiModelProperty(name = "算法模板名称", value = "algTemName")

    private String algTemName;

}
