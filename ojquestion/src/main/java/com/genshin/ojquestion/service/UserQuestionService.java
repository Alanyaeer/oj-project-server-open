package com.genshin.ojquestion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.domain.entity.UserQuestion;

/**
 * @author 吴嘉豪
 * @date 2024/1/2 22:59
 */
public interface UserQuestionService extends IService<UserQuestion> {
    void insert(Long titleId, Long userId);
}
