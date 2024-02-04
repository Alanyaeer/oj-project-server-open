package com.genshin.ojcommon.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2023/12/30 16:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "题目提交案例")
public class SubmitRecordsDto {
    @ApiModelProperty(value = "Integer", name = "题目提交通过所有案例（0为通过， 1为错误）")
    private Integer status;
    @ApiModelProperty(value = "Long", name = "题目提交创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "Long", name = "题目最后一个错误的案例数")
    private Integer lastCase;
    @ApiModelProperty(value = "Long", name = "题目最后一个错误的原因的映射（但是可以直接用HashMap 进行查询）")
    private Long lastReason;
}
