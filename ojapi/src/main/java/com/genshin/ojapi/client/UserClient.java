package com.genshin.ojapi.client;

import com.genshin.ojapi.dto.vo.UserCardShowVo;
import com.genshin.ojcommon.domain.vo.QuestionShowVo;
import com.genshin.ojcommon.domain.vo.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 吴嘉豪
 * @date 2024/1/6 11:31
 */
@FeignClient("oj-user")
public interface UserClient {
    @PostMapping("/userInner/getNickNameById")
    String getNickNameById(@RequestParam("id") Long id, @RequestHeader("token") String token);
    @PostMapping("/userInner/getUserCardInfoById")
    UserCardShowVo getUserCardInfoById(@RequestParam("fid") Long fid,@RequestHeader("token") String token);
    @PostMapping("/userInner/getThumbAndFavourByUserId")
    QuestionShowVo getThumbAndFavourByUserId(@RequestParam("id")String id, @RequestParam("userId") Long userId, @RequestHeader("token") String token);
    @PostMapping("/userInner/getCommentNumByIdType")
    Integer getCommentNumByIdType(@RequestParam("questionId") String questionId,@RequestParam("questionType") int questionType,@RequestHeader("token") String token);

    @PostMapping("/userInner/getUserInfo")
    UserInfoVo getUserInfo(@RequestParam("userId") Long userId, @RequestHeader("token") String token);
}
