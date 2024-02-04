package com.genshin.ojquestion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.domain.po.UserSolve;

/**
 * @author 吴嘉豪
 * @date 2024/1/2 22:59
 */
public interface UserSolveService extends IService<UserSolve> {
    boolean issue(Long titleId, Long userId);
}
