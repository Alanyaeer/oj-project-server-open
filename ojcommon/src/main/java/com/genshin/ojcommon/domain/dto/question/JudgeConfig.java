package com.genshin.ojcommon.domain.dto.question;

import lombok.Data;

/**
 * @author 吴嘉豪
 * @date 2024/1/5 20:26
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;

    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}
