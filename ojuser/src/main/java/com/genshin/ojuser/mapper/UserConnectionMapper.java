package com.genshin.ojuser.mapper;

import com.genshin.ojcommon.domain.entity.UserConnection;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/9 0:29
 */
public interface UserConnectionMapper {
    UserConnection getFollowStatus(@Param("userId") Long userId, @Param("friendId") Long friendId);
    Boolean followFriend(@Param("userId") Long userId, @Param("friendId") Long friendId, @Param("isNotFollow") Boolean isNotFollow);

    int selectCount(@Param("userId") Long userId,@Param("friendId") Long friendId);

    void insertConnection(@Param("userId") Long userId,@Param("friendId") Long friendId,@Param("isNotFollow") Boolean isNotFollow);
    List<UserConnection> getPageFollowFriend(@Param("offset") int offset,@Param("pageSize") int pageSize,@Param("userId") Long userId);

    List<UserConnection> getPageFollowMe(@Param("offset") int offset,@Param("pageSize") int pageSize,@Param("userId") Long userId);

    Integer getCountOfFollowing(Long userId);

    Integer getCountOfFollowed(Long userId);
}
