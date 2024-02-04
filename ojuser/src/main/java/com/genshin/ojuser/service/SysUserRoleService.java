package com.genshin.ojuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.entity.SysUserRole;

/**
 * @author 吴嘉豪
 * @date 2023/11/17 19:44
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    ResponseResult setUserPerm(Long id, Long type);
}
