package com.genshin.ojcommon.domain.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/10 0:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqDto implements Serializable {
    private Long id;
    private Integer add;
    //表示 点赞还是 收藏 0 表示点赞， 1表示 收藏
    private Integer addStatus;
    private Integer articleType;
}
