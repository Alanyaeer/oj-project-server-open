package com.genshin.ojcommon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 10:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    private Long id;

    private String category;
}
