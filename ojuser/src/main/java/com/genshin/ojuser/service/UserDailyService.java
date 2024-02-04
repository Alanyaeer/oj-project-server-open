package com.genshin.ojuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.domain.entity.UserDaily;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/3 22:18
 */
public interface UserDailyService extends IService<UserDaily> {
    List<UserDaily> selectByYear(int year, String userId, boolean isQueryProfile);

    void insert(String userId, String nowDate);
}
