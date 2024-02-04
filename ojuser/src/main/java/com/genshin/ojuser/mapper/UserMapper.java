package com.genshin.ojuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.genshin.ojcommon.domain.entity.User;
import com.genshin.ojapi.dto.vo.UserCardShowVo;


public interface UserMapper extends BaseMapper<User> {
    UserCardShowVo selectCard(Long id);
}
