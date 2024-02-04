package com.genshin.ojcommon.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/16 23:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteMessage implements Serializable {
    private Integer exitValue;
    private String message;
    private String errorMessage;
}
