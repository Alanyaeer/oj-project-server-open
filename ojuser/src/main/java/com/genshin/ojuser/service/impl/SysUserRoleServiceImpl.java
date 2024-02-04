package com.genshin.ojuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.entity.SysUserRole;
import com.genshin.ojuser.mapper.SysUserRoleMapper;
import com.genshin.ojuser.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 吴嘉豪
 * @date 2023/12/9 19:00
 */
@Service
@Slf4j
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public ResponseResult setUserPerm(Long id, Long type) {
        SysUserRole role = sysUserRoleMapper.selectById(id);
        log.info(role.toString());
        if(role.getRoleId() == 1L){
            return new ResponseResult(200, "你无法对管理者降级", 0);
        }
        role.setRoleId(type);
        sysUserRoleMapper.updateById(role);
//        update().setSql("role_id=type").eq("user_id", id).update();
       return new ResponseResult(200, "修改用户权限成功", 1);
    }
}
