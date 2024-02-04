package com.genshin.ojcommon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/5 21:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageClass implements Serializable {
    public Integer value;
    public String label;

}
