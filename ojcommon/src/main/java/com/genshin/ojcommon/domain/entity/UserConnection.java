package com.genshin.ojcommon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/8 23:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConnection implements Serializable {
    private Long id;
    private Long fid;
    private Boolean isDelete;
}
