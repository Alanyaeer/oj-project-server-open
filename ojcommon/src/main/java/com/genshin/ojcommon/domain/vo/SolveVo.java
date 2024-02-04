package com.genshin.ojcommon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/23 9:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolveVo implements Serializable {
    private Integer tryNum;
    private Integer passNum;
}
