package com.genshin.ojcommon.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2024/1/25 16:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitVo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String allStatus;

    private String time;

    private String memory;

    private String annotation;

    private String Language;
    private String code;
    private String titleName;
    private Integer titleId;
    private String errorMessage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "LocalDateTime", name = "题目提交更新时间")
    private LocalDateTime createTime;
}
