package com.genshin.ojquestion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/19 13:01
 */
public interface QuestionMapper extends BaseMapper<Question> {

    void updateLikes(@Param("questionId") Long questionId,@Param("add") Integer add);

    void updateFavourNum(@Param("questionId") Long questionId,@Param("add") Integer add);

    Question getRandomProblem();
}
