package com.genshin.ojcommon.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2023/12/11 13:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "标签列表")
public class TagList implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(name = "标签id", value = "id")
    private Integer id ;

    @ApiModelProperty(name = "标签名称", value = "id")
    private String tagName;
}
