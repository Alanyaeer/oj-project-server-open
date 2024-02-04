package com.genshin.ojuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.domain.entity.ThumbAndFavour;
import com.genshin.ojcommon.domain.vo.QuestionShowVo;
import io.swagger.models.auth.In;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 12:13
 */
public interface ThumbAndFavourService extends IService<ThumbAndFavour> {
    boolean thumb(boolean isThumb, String sourceId, Integer type);

    boolean favour(boolean isFavour, String sourceId, Integer type);

    Integer judgeDecrOrAdd(boolean isThumb, int i, String sourceId, Integer type);

    QuestionShowVo getThumbAndFavourByUserId(String id, Long userId, String token);
}
