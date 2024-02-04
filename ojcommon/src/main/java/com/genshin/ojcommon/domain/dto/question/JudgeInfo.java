package com.genshin.ojcommon.domain.dto.question;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/5 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeInfo implements Serializable {
    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 消耗时间（KB）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long time;
    /**
     * 消耗内存
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memory;


}
