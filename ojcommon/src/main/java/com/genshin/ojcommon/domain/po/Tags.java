package com.genshin.ojcommon.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 吴嘉豪
 * @date 2023/12/11 13:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "标签关联类实体类")
@Deprecated
public class Tags {
    @TableId
    @ApiModelProperty(name = "关联id", value = "id")
    private Long id;
    @ApiModelProperty(name = "关联的标签id", value = "idTag")

    private Long idTag;
    @ApiModelProperty(name = "关联类型 0 代表关联题目， 1 代表关联算法模板", value = "type")

    private Integer type;
    @ApiModelProperty(name = "关联来源的id", value = "sourceId")

    private Long sourceId;

}
