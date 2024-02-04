package com.genshin.ojapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2023/12/30 18:15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDto implements Serializable {
    // 这个id 暂时废弃
    String id;
    String location;
    Integer status;
    Long lastReason;
    Integer lastcase;
    String titleId;

}
