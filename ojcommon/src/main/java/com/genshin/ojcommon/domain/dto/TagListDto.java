package com.genshin.ojcommon.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/20 13:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "接受标签的列名和问题名称")
public class TagListDto {
    @ApiModelProperty(name = "标签的id列表")
    public String tagIdList;
    @ApiModelProperty(name = "标题")
    public String titleName;
}
