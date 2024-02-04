package com.genshin.ojuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.genshin.ojcommon.domain.entity.UserDaily;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/3 22:18
 */

public interface UserDailyMapper extends BaseMapper<UserDaily> {
   List<UserDaily> SelectDataByYear(@Param("year")int year, @Param("userId") Long userId);

   List<UserDaily> SelectDataForLatestByNow(Long aLong);

   List<UserDaily> UpdateDataForId(@Param("time") String time,@Param("id") Long id);
}
