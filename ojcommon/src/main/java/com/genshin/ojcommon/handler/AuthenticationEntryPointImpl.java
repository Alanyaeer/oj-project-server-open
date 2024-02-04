package com.genshin.ojcommon.handler;

import com.alibaba.fastjson.JSON;

import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 吴嘉豪
 * @date 2023/11/15 15:03
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    // 认证失败
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "用户认证失败， 请重新登录");
        String json = JSON.toJSONString(result);

        // 处理异常
        WebUtils.renderString(response, json);
    }
    //
}
