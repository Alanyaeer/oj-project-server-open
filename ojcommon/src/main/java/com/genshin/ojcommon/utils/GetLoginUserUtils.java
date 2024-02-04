package com.genshin.ojcommon.utils;

import com.genshin.ojcommon.domain.entity.LoginUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 吴嘉豪
 * @date 2024/1/5 16:56
 */
public class GetLoginUserUtils {
    public static LoginUser getLoginUser(){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        return loginUser;
    }
    public static Long getUserId(){
        return getLoginUser().getUser().getId();
    }

    public static boolean isManager() {
        return getLoginUser().getPermissions().contains("manager");
    }
    public static boolean isPmk(){
        return getLoginUser().getPermissions().contains("pmk");
    }
}
