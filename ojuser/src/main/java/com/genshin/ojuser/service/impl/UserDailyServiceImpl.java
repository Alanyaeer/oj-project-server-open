package com.genshin.ojuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojcommon.domain.entity.UserDaily;
import com.genshin.ojuser.mapper.UserDailyMapper;
import com.genshin.ojuser.service.UserDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/3 22:18
 */
@Service
public class UserDailyServiceImpl extends ServiceImpl<UserDailyMapper, UserDaily> implements UserDailyService {
    @Autowired
    private UserDailyMapper userDailyMapper;
    @Override
    public List<UserDaily> selectByYear(int year, String userId, boolean isQueryProfile) {
        if(isQueryProfile)
            return userDailyMapper.SelectDataByYear(year, Long.valueOf(userId));
        else
            return userDailyMapper.SelectDataForLatestByNow(Long.valueOf(userId));
//        return null;
    }

    @Override
    public void insert(String userId, String nowDate) {
        Long id = Long.valueOf(userId);
        UserDaily daily = new UserDaily();
        daily.setId(id);
        daily.setTimes(1);
        userDailyMapper.insert(daily);
        System.out.println(daily);
    }
}
