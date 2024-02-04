package com.genshin.ojcommon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/27 15:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageNumVo implements Serializable {
    private String language;
    private Integer solveNum;
}
