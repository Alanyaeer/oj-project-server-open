package com.genshin.ojcommon.domain.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴嘉豪
 * @date 2024/1/11 10:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmitAddRequest implements Serializable {
    private Long questionId;
    private String code;
    private Integer language;
}
