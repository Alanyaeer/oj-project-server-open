package com.genshin.ojcommon.domain.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/16 11:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitQuestionWithTokenDto implements Serializable {
    private String message;

    private String token;
}
