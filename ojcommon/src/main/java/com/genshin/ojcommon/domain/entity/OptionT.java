package com.genshin.ojcommon.domain.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author 吴嘉豪
 * @date 2023/11/30 9:52
 */
@Data
@Builder
@Deprecated
public class OptionT {
    private String b;
    private String a;
    private Integer c;
}
