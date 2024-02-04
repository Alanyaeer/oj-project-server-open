package com.genshin.ojcommon.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/1 21:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "测试用例类")
public class CaseDto implements Serializable {
    private String outputText;
    private String inputText;
}
