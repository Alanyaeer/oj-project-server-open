package com.genshin.ojcommon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.security.SecureRandom;

/**
 * @author 吴嘉豪
 * @date 2024/1/27 15:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolveMsgVo implements Serializable {
    private String language;
    private Integer num;
}
