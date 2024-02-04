package com.genshin.ojcommon.handler;

import com.alibaba.fastjson.JSON;

import com.genshin.ojcommon.common.ResponseResult;

import com.genshin.ojcommon.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 吴嘉豪
 * @date 2023/11/15 15:08
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "你的权限不足");
        String json = JSON.toJSONString(result);

        // 处理异常
        WebUtils.renderString(response, json);
    }
}
