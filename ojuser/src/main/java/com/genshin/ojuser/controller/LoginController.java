package com.genshin.ojuser.controller;


import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.entity.User;
import com.genshin.ojuser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 吴嘉豪
 * @date 2023/11/14 16:36
 */

@RestController
@RequestMapping("/user")
@Api(tags="用户登录出入相关接口")
public class LoginController {
    @Autowired
    private UserService userService;
    @ApiOperation("用户登录接口")
    @PostMapping("/login")

    public ResponseResult login(@RequestBody User user) {
        return userService.login(user);
    }
//    @PutMapping("/user/register")
    @ApiOperation("用户注册接口")
    @PutMapping("/register")
    public ResponseResult register(@RequestBody User user){
        String userName = user.getUserName();
        String password = user.getPassword();
        return userService.register(userName, password);
    }


    @PostMapping("/logout")
    @ApiOperation("用户登出接口")
    @PreAuthorize("hasAnyAuthority('vip')")
    public ResponseResult logout(){
        return userService.logout();
    }
}
