package com.genshin.ojuser.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.dto.user.UserProfileRequest;
import com.genshin.ojcommon.domain.entity.User;
import com.genshin.ojapi.dto.vo.UserCardShowVo;
import com.genshin.ojcommon.domain.vo.UserInfoVo;

/**
 * @author 吴嘉豪
 * @date 2023/11/14 16:39
 */
public interface UserService extends IService<User> {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult register(String userName, String password);
    ResponseResult update(UserProfileRequest user);
    ResponseResult queryUser(int page, int pageSize, String nickName);

    ResponseResult deleteUser(Long id);

    ResponseResult addManagerUser(String userName, String password);

    ResponseResult restoreUser(Long id);

    ResponseResult getTotalCount();

    ResponseResult deleteUserByuserName(String userName);

    UserInfoVo queryUserInfo(Long userId);

    boolean updateOwerId(Long ownerId, Integer add, Integer type);

    UserCardShowVo selectCardById(Long fid, Long id);
}
