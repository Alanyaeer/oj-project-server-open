package com.genshin.ojuser.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.dto.user.UserProfileRequest;
import com.genshin.ojcommon.domain.entity.User;
import com.genshin.ojcommon.domain.entity.UserConnection;
import com.genshin.ojcommon.domain.entity.UserDaily;
import com.genshin.ojcommon.domain.vo.SolveMsgVo;
import com.genshin.ojcommon.domain.vo.UserInfoVo;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojcommon.utils.RedisCache;
import com.genshin.ojuser.mapper.UserConnectionMapper;
import com.genshin.ojuser.service.UserDailyService;
import com.genshin.ojuser.service.UserService;
import com.rabbitmq.client.AMQP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.genshin.ojcommon.constants.RedisConstants.GET_SOLVE_LANGUAGE_NUM;

/**
 * @author 吴嘉豪
 * @date 2023/11/17 20:11
 */
@Slf4j
@RestController
@RequestMapping("/profile")
@Api(tags="用户信息保存")
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDailyService userDailyService;
    @Autowired
    private UserConnectionMapper userConnectionMapper;
    @Autowired
    private RedisCache redisCache;
    @PutMapping("/updateProfile")
    @PreAuthorize("hasAnyAuthority('vip')")
    @ApiOperation(value = "用户信息保存提交接口")
    public ResponseResult profileSave(@RequestBody UserProfileRequest user){
        return userService.update(user);
    }

    @ApiOperation(value = "获取用户信息")
    @PostMapping("/queryUserInfo")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult queryUserInfo(String id){
        Long userId = null;
        // 说明是查询自己
        if(StrUtil.isBlank(id))
            userId = GetLoginUserUtils.getUserId();
        // 说明是查询别人
        else userId = Long.valueOf(id);
        UserInfoVo userInfoVo =  userService.queryUserInfo(userId);
        return new ResponseResult(200, "获取用户信息", userInfoVo);
    }
    /**
     *
     * @param year
     * @param
     * @param isQueryProfile
     * @return
     */
    @PostMapping("/getUserDayLife")
    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation(value = "用户获取月活跃度")
    // TODO 改造为 Map 类型
    // 是否是页面展示的查询，年份， 用户id
    // List<> 返回365天内的月活
    // 远程调用查询访问当天频率
    public ResponseResult queryUserDayLife(int year,  boolean isQueryProfile, String userId){
        // 如果说 查询的不是自己的话
        if(StrUtil.isEmpty(userId)){
            userId = GetLoginUserUtils.getUserId().toString();
        }
        List<UserDaily> dailyList= userDailyService.selectByYear(year, userId, isQueryProfile);
//        Map<String, Integer> getLive = new LinkedHashMap<>();
        if(isQueryProfile == false){
            Map<String, Integer> getLive = new LinkedHashMap<>();
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 添加从今天到前84天的键值对
            for (int i = 83; i >= 0; i--) {
                LocalDate date = today.minusDays(i);
                String formattedDate = formatter.format(date);
                getLive.put(formattedDate, 0); // 这里的值可以根据实际需求设置
            }
            for (UserDaily daily : dailyList) {
                getLive.put(String.valueOf(daily.getCreateTime()).split("T")[0], daily.getTimes());
            }
            return new ResponseResult(200, "查询成功", getLive);
        }
        else {
            Map<String, Integer> getLive = new HashMap<>();
            for (UserDaily daily : dailyList) {
                getLive.put(String.valueOf(daily.getCreateTime()).split("T")[0], daily.getTimes());
            }
            return new ResponseResult(200, "查询成功", getLive);

        }

    }
    // TODO 未来需要在题目回答正确之后， 添加消息队列
    @PostMapping("/addUserDayLife")
    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation(value = "用户添加活跃度")
    public ResponseResult addUserDayLife(){
        String userId = GetLoginUserUtils.getUserId().toString();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(formatter.format(date));
        String nowDate = formatter.format(date);
//        userDailyService
        userDailyService.update().setSql("times = times + 1").eq("create_time", nowDate).eq("id", Long.valueOf(userId)).update();
        if(userDailyService.count(new LambdaQueryWrapper<UserDaily>().eq(UserDaily::getId, userId).eq(UserDaily::getCreateTime, nowDate)) == 0){
            userDailyService.insert(userId, nowDate);
        }
        return new ResponseResult(200, "修改完成", 1);
    }

    /**
     * 是否关注这个好友
     * @param friendId
     * @param isNotFollow
     * @return
     */
    @ApiOperation("关注好友")
    @PreAuthorize("hasAuthority('vip')")
    @PostMapping("/followFriend")
    public ResponseResult followFriend(String friendId, Boolean isNotFollow){
        Long userId = GetLoginUserUtils.getUserId();
        int count =  userConnectionMapper.selectCount(userId, Long.valueOf(friendId));
        if(userId == Long.valueOf(friendId)) return new ResponseResult(200, "关注失败", 0);
        if(count == 0){
            userConnectionMapper.insertConnection(userId, Long.valueOf(friendId), isNotFollow);
        }
        else{
            userConnectionMapper.followFriend(userId, Long.valueOf(friendId), isNotFollow);
        }
        return  new ResponseResult(200, "关注结束" ,1);
    }

    @ApiOperation("获取我的所有关注列表")
    @PreAuthorize("hasAuthority('vip')")
    @PostMapping("/queryFollowFriends")
    public ResponseResult queryFollowFriends(int page, int pageSize){
        Long userId = GetLoginUserUtils.getUserId();
        int offset = (page - 1) * pageSize;
        List<UserConnection>  pageList =   userConnectionMapper.getPageFollowFriend(offset, pageSize, userId);
        return new ResponseResult<>(200, "获取关注列表", pageList);
    }
    @ApiOperation("获取关注我的人的列表")
    @PreAuthorize("hasAuthority('vip')")
    @PostMapping("/queryFollowMe")
    public ResponseResult queryFollowMe(int page,int pageSize){
        Long userId = GetLoginUserUtils.getUserId();
        int offset = (page - 1) * pageSize;
        List<UserConnection> pageList =  userConnectionMapper.getPageFollowMe(offset, pageSize, userId);
        return new ResponseResult<>(200, "获取被关注列表", pageList);

    }
}
