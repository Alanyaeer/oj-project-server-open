package com.genshin.ojuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.genshin.ojcommon.domain.entity.Menu;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/11/15 14:07
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId( Long userid);


}
