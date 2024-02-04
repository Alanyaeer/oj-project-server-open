package com.genshin.ojcommon.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2024/1/28 17:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitProfileVo implements Serializable {
    // 题目的id
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String titleName;
    private String titleId;
    private Integer score;
    private Integer submitNum;
    private LocalDateTime createTime;
}
