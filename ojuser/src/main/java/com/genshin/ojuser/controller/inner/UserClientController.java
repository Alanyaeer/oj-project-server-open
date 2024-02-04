package com.genshin.ojuser.controller.inner;

import cn.hutool.core.util.StrUtil;
import com.genshin.ojapi.client.UserClient;
import com.genshin.ojapi.dto.vo.UserCardShowVo;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.entity.UserConnection;
import com.genshin.ojcommon.domain.vo.QuestionShowVo;
import com.genshin.ojcommon.domain.vo.SolveMsgVo;
import com.genshin.ojcommon.domain.vo.UserInfoVo;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojcommon.utils.RedisCache;
import com.genshin.ojuser.mapper.CommentMapper;
import com.genshin.ojuser.mapper.UserConnectionMapper;
import com.genshin.ojuser.mapper.UserMapper;
import com.genshin.ojuser.service.ThumbAndFavourService;
import com.genshin.ojuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

import static com.genshin.ojcommon.constants.RedisConstants.GET_SOLVE_LANGUAGE_NUM;

/**
 * @author 吴嘉豪
 * @date 2024/1/6 11:33
 */
@RestController
@RequestMapping("/userInner")
public class UserClientController implements UserClient {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ThumbAndFavourService thumbAndFavourService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserConnectionMapper userConnectionMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    @PostMapping("/getNickNameById")
    public String getNickNameById(Long id, String token) {
        return userMapper.selectById(id).getNickName();
    }

    @Override
    @PostMapping("/getUserCardInfoById")
    public UserCardShowVo getUserCardInfoById(Long fid ,String token){
        Long id = GetLoginUserUtils.getUserId();
        UserCardShowVo userCardShowVo = userService.selectCardById(fid, id);
        return userCardShowVo;
    }

    @Override
    @PostMapping("/getThumbAndFavourByUserId")
    public QuestionShowVo getThumbAndFavourByUserId(String id, Long userId, String token) {
//        thumbAndFavourService.get
       QuestionShowVo questionShowVo =   thumbAndFavourService.getThumbAndFavourByUserId(id, userId, token);
       return questionShowVo;
    }
    @PostMapping("/getCommentNumByIdType")
    @Override
    public Integer getCommentNumByIdType(String questionId, int questionType, String token) {
        int i = commentMapper.queryCommentNumByIdType(Long.valueOf(questionId), questionType);
        return i;
    }
    @PostMapping("/getUserInfo")
    @Override
    public UserInfoVo getUserInfo(Long userId, String token) {
        UserInfoVo userInfoVo =  userService.queryUserInfo(userId);
//        // 获取关注个数
//        userInfoVo.setFollowing(userConnectionMapper.getCountOfFollowing(userId));
//        userInfoVo.setFollowed(userConnectionMapper.getCountOfFollowed(userId));
//        // 获取用户解题的个数
//        Map<String, Integer> cacheMap = redisCache.getCacheMap(GET_SOLVE_LANGUAGE_NUM + ":" + userId);
//        ArrayList<SolveMsgVo> solveMsgVosList = new ArrayList<>();
//
//        if(!CollectionUtils.isEmpty(cacheMap)) {
//            for (String s : cacheMap.keySet()) {
//                SolveMsgVo vo = new SolveMsgVo(s, cacheMap.get(s));
//                solveMsgVosList.add(vo);
//            }
//            userInfoVo.setSolveMsgVoList(solveMsgVosList);
//        }
//        else userInfoVo.setSolveMsgVoList(solveMsgVosList);
//        if( userId ==  GetLoginUserUtils.getUserId()) {
//            userInfoVo.setSelf(true);
//        }
//        else {
//            userInfoVo.setSelf(false);
//            UserConnection followStatus = userConnectionMapper.getFollowStatus(GetLoginUserUtils.getUserId(), userId);
//            if(followStatus == null)
//                userInfoVo.setFollowPerson(false);
//            else userInfoVo.setFollowPerson(!followStatus.getIsDelete());
//        }
        return  userInfoVo;
    }

}
