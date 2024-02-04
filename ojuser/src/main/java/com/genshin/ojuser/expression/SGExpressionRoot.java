package com.genshin.ojuser.expression;


import com.genshin.ojcommon.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/11/15 16:01
 */
@Component("ex")

public class SGExpressionRoot {
    public boolean hasAuthority(String authority){
        // 获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();

        // 判断用户权限集合中是否存在authority
        return permissions.contains(authority);
    }
}
