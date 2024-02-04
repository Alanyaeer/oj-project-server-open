package com.genshin.ojquestion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojcommon.domain.po.UserSolve;
import com.genshin.ojquestion.mapper.UserSolveMapper;
import com.genshin.ojquestion.service.UserQuestionService;
import com.genshin.ojquestion.service.UserSolveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 吴嘉豪
 * @date 2024/1/2 22:59
 */
@Service
public class UserSolveServiceImpl extends ServiceImpl<UserSolveMapper, UserSolve> implements UserSolveService {
    @Autowired
    private UserSolveMapper userSolveMapper;
    @Override
    public boolean issue(Long titleId,Long userId) {
        int count = count(new LambdaQueryWrapper<UserSolve>().eq(UserSolve::getTitleId, titleId).eq(UserSolve::getId, userId));
        if(count == 0){
            return true;

        }
        else {
            UserSolve build = UserSolve.builder().
                    id(userId).
                    titleId(titleId).build();
            userSolveMapper.insert(build);
            return false;
        }
    }
}
