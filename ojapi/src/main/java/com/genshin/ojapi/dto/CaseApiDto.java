package com.genshin.ojapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/1 21:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseApiDto implements Serializable {
    private String outputText;
    private String inputText;
}