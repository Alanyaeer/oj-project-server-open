package com.genshin.ojcommon.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/21 17:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "管理者审核题目的列表")
public class QuestionManaReturnVo extends QuestionReturnVo {
//    private QuestionReturnDto questionReturnDto;
    @JsonSerialize(using = ToStringSerializer.class)

    @ApiModelProperty(value = "id", name = "题目id")
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private Integer questionType;
    @ApiModelProperty(value = "Integer", name = "通过人数")
    private Integer passPerson;
    @ApiModelProperty(value = "Integer", name = "尝试人数")
    private Integer tryPerson;

    // 需要先获取通过createUserId 获取到用户当前的nickName
    @ApiModelProperty(value = "createBy", name = "创建人")
    private String createBy;
    @ApiModelProperty(value = "likes", name = "点赞数")
    private Integer likes;
    @ApiModelProperty(value = "收藏个数")
    private Integer favourNum;
    @ApiModelProperty(value = "tags")
    private List<String> tags;
    @ApiModelProperty(value = "titleId")
    private Integer titleId;
}
