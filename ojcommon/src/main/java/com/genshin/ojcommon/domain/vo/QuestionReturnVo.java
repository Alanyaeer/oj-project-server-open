package com.genshin.ojcommon.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/20 20:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "返回的问题的序列")
public class QuestionReturnVo {
//    @Serial
//    @JsonSerialize()
//    @JSONField(serializeUsing = ToStringSerializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "id", name = "题目id")
    private Long id;
    @ApiModelProperty(value = "titleName", name = "问题的名称")
    private String titleName;

    @ApiModelProperty(value = "likes", name = "点赞数")
    private Integer likes;
    @ApiModelProperty(value = "Integer", name = "通过人数")

    private Integer passPerson;

    @ApiModelProperty(value = "score", name = "分数")
    private Integer score;


    @ApiModelProperty(value = "提交人数",name = "提交人数")
    private Integer submitNum;

    @ApiModelProperty(value = "收藏个数")
    private Integer favourNum;
    /**
     * 需要先转换为 String 类型， 原本存储的是 数字
     */
    @ApiModelProperty(value = "tags")
    private List<String> tags;
    /**
     * orderBy titleId;
     */
    @ApiModelProperty(value = "titleId")
    private Integer titleId;
    @ApiModelProperty(value = "status")
    private boolean status;

    private boolean tryStatus;
}
