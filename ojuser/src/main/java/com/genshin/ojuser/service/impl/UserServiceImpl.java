package com.genshin.ojuser.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojcommon.common.ErrorCode;
import com.genshin.ojcommon.domain.dto.user.UserProfileRequest;
import com.genshin.ojcommon.domain.entity.LoginUser;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.entity.SysUserRole;
import com.genshin.ojcommon.domain.entity.User;
import com.genshin.ojapi.dto.vo.UserCardShowVo;
import com.genshin.ojcommon.domain.entity.UserConnection;
import com.genshin.ojcommon.domain.vo.SolveMsgVo;
import com.genshin.ojcommon.domain.vo.UserInfoVo;
import com.genshin.ojcommon.exception.BusinessException;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojuser.mapper.SysUserRoleMapper;
import com.genshin.ojuser.mapper.UserConnectionMapper;
import com.genshin.ojuser.mapper.UserMapper;

import com.genshin.ojcommon.utils.JwtUtil;
import com.genshin.ojcommon.utils.RedisCache;
import com.genshin.ojuser.mapper.UserRoleMapper;
import com.genshin.ojuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.genshin.ojcommon.constants.RedisConstants.*;

/**
 * @author 吴嘉豪
 * @date 2023/11/14 16:39
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserConnectionMapper userConnectionMapper;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断上面这个是不是为null， 如果为null 说明没有通过
        if(Objects.isNull(authenticate)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不存在");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 将用户的id 添加进去
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        User userOne = userMapper.selectById(userid);
        if(userOne.getDelFlag() == 1) throw new BusinessException(ErrorCode.PARAMS_ERROR, "你的账号已经被封禁了");
        // 认证通过
        // authenticate.getPrincipal()
        // 把完整用户信息存入到redis 中
        redisCache.setCacheObject("login: " + userid , loginUser);
        return new ResponseResult(200, "登录成功", jwt);
    }

    //  前端需要携带token返回。
    @Override
    public ResponseResult logout() {
        // 获取securityContextHolder 中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        redisCache.deleteObject("login: "+userid);
        // 删除redis当中的值
        return new ResponseResult(200, "注销成功");
    }

    @Override
    public ResponseResult register(String userName, String password) {
        return registerType(userName, password, 2L);
    }

    /**
     *
     * @param userName
     * @param password
     * @param userType
     * @return ResponseResult
     * @apiNote 注册用户类型 userType = 1 管理员用户 userType = 2 普通用户
     *
     */
    private ResponseResult registerType(String userName, String password, Long userType) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        User user = userMapper.selectOne(wrapper);
        if(Objects.isNull(user) == false){
            return new ResponseResult(200, "已存在该用户名", 0);
        }
        User userNew = new User();
        // 使用 BCryptPasswordEncoder进行判断
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);

        userNew.setPassword(encode);
        userNew.setUserName(userName);
        userNew.setAvatar("https://picsum.photos/60/60");
        userNew.setNickName("Genshin");
        userNew.setFavourNum(0);
        userNew.setThumbNum(0);
        userNew.setDescription("");
        userNew.setFollowing(0);
        userNew.setFollowers(0);

        userMapper.insert(userNew);
        // 重新获取到用户的id， 然后
        User userone = userMapper.selectOne(wrapper);
        Long id = userone.getId();
        // 存入到sysuserroleMapper里面
        sysUserRoleMapper.insert(new SysUserRole(id, userType));
        // 未测试TODO
        redisCache.setCacheSingleList(GET_USER_INFO_LIST, userone);

        // 建立redis字段
        initKey(user);

        return new ResponseResult(200, "注册成功", 1);
    }
    void initKey(User user){
        // 建立字段；
        for(int i = 0; i < 3; ++i){
            redisCache.setCacheObject(PASS_PROBLEM_MESSAGE + ":" + user.getId() + ":" + i, 0);
            redisCache.setCacheObject(PASS_PROBLEM_PERSON + ":" + user.getId() + ":" +i, 0);
            redisCache.setCacheObject(TRY_PROBLEM_PERSON + ":" + user.getId() + ":" + i, 0);

        }
    }
    @Override
    public ResponseResult update(UserProfileRequest user) {
        // 获取securityContextHolder 中的用户id
        Long userId = GetLoginUserUtils.getUserId();
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        user1.setId(userId);
//        user.setId(userId);
        userMapper.updateById(user1);
        return new ResponseResult(200, "更新用户信息成功" ,1);
    }
    /**
     *
     * @param page
     * @param pageSize
     * @param nickName
     * @return ResponseResult<Page<User>>
     */
    @Override
    public ResponseResult queryUser(int page, int pageSize, String nickName) {
        // 构造页数器
        Page<User> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StrUtil.isBlank(nickName), User::getNickName, nickName);
        queryWrapper.orderByAsc(User::getDelFlag);
        page(pageInfo, queryWrapper);

        List<User> userList = pageInfo.getRecords();
//        String userType;
        userList = userList.stream().map(e->{
            Long id = e.getId();
            SysUserRole role = userRoleMapper.selectById(id);
            e.setUserType(role.getRoleId().toString());
            e.setPassword("");
            return e;
        }).collect(Collectors.toList());
        queryWrapper.orderByAsc(User::getUpdateTime);
        // TODO 这里需要优化， 因为 有可能出现同时进行redisCache优化的 这样就麻烦了
        return new ResponseResult(200, "查询成功", userList);
    }

    /**
     *
     * @param id
     * @return ResponseResult
     * @apiNote 对于具有管理者权限的用户，你不能删除
     */
    @Override
    public ResponseResult deleteUser(Long id) {
        ResponseResult result = getResult(id);
        if (result != null) return result;
        update().setSql("del_flag = 1").eq("id", id).update();
        return new ResponseResult(200, "删除成功", 1);
    }

    private ResponseResult getResult(Long id) {
        SysUserRole role = sysUserRoleMapper.selectById(id);
        if(role.getRoleId() == 1) return new ResponseResult(200, "删除失败，你不能删除管理者", 0);
        return null;
    }

    @Override
    public ResponseResult addManagerUser(String userName, String password) {

        return registerType(userName, password, 1L);
    }

    @Override
    public ResponseResult restoreUser(Long id) {
        update().setSql("del_flag = 0").eq("id", id).update();
        return new ResponseResult(200, "用户已被恢复正常", 1);
    }

    @Override
    public ResponseResult getTotalCount() {
        Long ans = Long.valueOf(query().count());
        System.out.printf(String.valueOf(ans));
        return new ResponseResult<>(200, "查询用户个数", ans);
    }

    @Override
    public ResponseResult deleteUserByuserName(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        ResponseResult result = getResult(userMapper.selectOne(queryWrapper).getId());
        if(result != null) return result;

        update().setSql("del_flag=1").eq("user_name", userName).update();
        return new ResponseResult(200, "删除用户成功", 1);
//        userMapper.delete(query().getWrapper().eq(User::getUserName, userName));
    }

    @Override
    public UserInfoVo queryUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);
        // 获取关注个数
        userInfoVo.setFollowing(userConnectionMapper.getCountOfFollowing(userId));
        userInfoVo.setFollowed(userConnectionMapper.getCountOfFollowed(userId));
        // 获取用户解题的个数
        Map<String, Integer> cacheMap = redisCache.getCacheMap(GET_SOLVE_LANGUAGE_NUM + ":" + userId);
        ArrayList<SolveMsgVo> solveMsgVosList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(cacheMap)) {
            for (String s : cacheMap.keySet()) {
                SolveMsgVo vo = new SolveMsgVo(s, cacheMap.get(s));
                solveMsgVosList.add(vo);
            }
            userInfoVo.setSolveMsgVoList(solveMsgVosList);
        }
        else userInfoVo.setSolveMsgVoList(solveMsgVosList);
        if(userId.equals(GetLoginUserUtils.getUserId())) {
            userInfoVo.setSelf(true);
        }
        else {
            userInfoVo.setSelf(false);
            UserConnection followStatus = userConnectionMapper.getFollowStatus(GetLoginUserUtils.getUserId(), userId);
            if(followStatus == null)
                userInfoVo.setFollowPerson(false);
            else userInfoVo.setFollowPerson(!followStatus.getIsDelete());
        }
        return userInfoVo;
    }

    @Override
    public boolean updateOwerId(Long ownerId, Integer add, Integer type) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, ownerId);

        User user = userMapper.selectOne(wrapper);
        Integer thumbNum = user.getThumbNum();
        Integer favourNum = user.getFavourNum();

        // 0代表点赞 1 代表收藏
        if(type == 0){
            user.setThumbNum(thumbNum + add);
            userMapper.updateById(user);
        }
        else{
            user.setFavourNum(favourNum + add);
            userMapper.updateById(user);
        }
        return true;

    }

    @Override
    public UserCardShowVo selectCardById(Long fid,  Long id) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,id);
        wrapper.select(
                User::getNickName,
                User::getAvatar,
                User::getFavourNum,
                User::getThumbNum,
                User::getUpdateTime,
                User::getDescription,
                User::getFollowers
        );
        User user = userMapper.selectOne(wrapper);
        UserCardShowVo showVo = new UserCardShowVo();
        BeanUtils.copyProperties(user, showVo);
        showVo.setUserDescription(user.getDescription());
        showVo.setUserFavourNum(user.getFavourNum());
        showVo.setUserThumbNum(user.getThumbNum());
        // 放置出现 null的情况
//        Boolean status = Boolean.valueOf(userConnectionMapper.getFollowStatus(id, fid)) ;
        UserConnection followStatus = userConnectionMapper.getFollowStatus(id, fid);
        if(followStatus != null && followStatus.getIsDelete() == false)
        showVo.setIsFollow(true);
        else showVo.setIsFollow(false);
        return showVo;
    }

}
