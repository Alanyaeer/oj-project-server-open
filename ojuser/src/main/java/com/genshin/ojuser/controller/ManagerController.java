package com.genshin.ojuser.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.entity.User;
import com.genshin.ojcommon.domain.entity.UserConnection;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojcommon.utils.RedisCache;
import com.genshin.ojuser.mapper.UserConnectionMapper;
import com.genshin.ojuser.service.SysUserRoleService;
import com.genshin.ojuser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.genshin.ojcommon.constants.RedisConstants.GET_USER_INFO_LIST;

/**
 * @author 吴嘉豪
 * @date 2023/12/9 17:00
 */
@RestController
@RequestMapping("/manager")
@Slf4j
@Api("管理者的接口")
public class ManagerController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private RedisCache redisCache;

    @ApiOperation("查询所有的用户")
    @PreAuthorize("hasAuthority('manager')")
    @GetMapping("/userPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value="page", name = "页数"),
            @ApiImplicitParam(value="pageSize", name = "页面大小"),
            @ApiImplicitParam(value="nickName", name = "用户昵称")
    })

    public ResponseResult queryAllUserPage(int page, int pageSize, String nickName) {
        List<User> cacheList = redisCache.getCacheList(GET_USER_INFO_LIST, page, pageSize);
        if(CollectionUtil.isEmpty(cacheList) == false){
            return new ResponseResult(200, "查询成功", cacheList);
        }
        return userService.queryUser(page, pageSize, nickName);
    }

    /**
     *
     * @param id
     * @return
     * @apiNote 这里不是真的删除用户，而是逻辑删除 del_flag = 1
     */
    @ApiOperation("删除用户")
    @PreAuthorize("hasAuthority('manager')")
    @DeleteMapping("/deleteUser")
    @ApiImplicitParam(value = "id", name = "用户id")
    public ResponseResult deleteUser(Long id){
        return userService.deleteUser(id);
    }
    @ApiOperation("删除用户通过用户名")
    @PreAuthorize("hasAuthority('manager')")
    @DeleteMapping("/deleteUserByUserName")
    @ApiImplicitParam(value = "userName", name = "用户名")
    public ResponseResult deleteUserByUserName(String userName){
        return userService.deleteUserByuserName(userName);
    }
    /**
     *
     * @param id
     * @return
     * @apiNote  恢复用户的状态 == 从删除状态变为正常状态
     */
    @ApiOperation("恢复被删除的用户")
    @PreAuthorize("hasAuthority('manager')")
    @PutMapping("/restoreUser")
    @ApiImplicitParam(value = "id", name = "用户id")
    public ResponseResult restoreUser(Long id){
        return userService.restoreUser(id);
    }


    /**
     *
      * @param userName
     * @param password
     * @return
     */
    @ApiOperation("添加管理员用户")
    @PreAuthorize("hasAuthority('manager')")
    @PutMapping("/addUser")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "userName", name ="用户名称"),
            @ApiImplicitParam(value = "password", name ="用户密码")
    })
    public ResponseResult addManagerUser(String userName, String password){
        return userService.addManagerUser(userName, password);
    }

    /**
     *
     * @param id
     * @return ResponseResult
     * @apiNote  这是用于设置用户权限的
     */
    @ApiOperation("设置用户权限")
    @PreAuthorize("hasAuthority('manager')")
    @PutMapping("/setUserPerm")
    @ApiImplicitParams({
            @ApiImplicitParam(value= "id", name="用户id")
    })
    public ResponseResult setUserPerm(Long id, Long type){
        return userRoleService.setUserPerm(id, type);
    }

    @ApiOperation("查看用户个数")
    @PreAuthorize("hasAuthority('manager')")
    @GetMapping("/queryTotalUserCount")
    public ResponseResult queryTotalCount() {
        return userService.getTotalCount();
    }


    /**
     *
     * @param friendId
     * @param  isNotfollow 用户是否是关注， false代表 关注 true 代表 不关注
     *
     * @return
     */

}
