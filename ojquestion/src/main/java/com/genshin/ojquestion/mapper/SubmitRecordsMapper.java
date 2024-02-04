package com.genshin.ojquestion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.vo.SubmitProfileVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/30 15:31
 */

public interface SubmitRecordsMapper extends BaseMapper<SubmitRecords> {
    List<SubmitProfileVo> getProfileSubmitList(@Param("userId") Long userId,@Param("condition") String condition);
    SubmitRecords getLatestSubmit(String userId);

}
