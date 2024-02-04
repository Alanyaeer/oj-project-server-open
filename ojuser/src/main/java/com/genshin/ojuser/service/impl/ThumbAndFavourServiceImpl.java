package com.genshin.ojuser.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojcommon.common.ErrorCode;
import com.genshin.ojcommon.domain.entity.ThumbAndFavour;
import com.genshin.ojcommon.domain.vo.QuestionShowVo;
import com.genshin.ojcommon.exception.BusinessException;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojuser.mapper.ThumbAndFavourMapper;
import com.genshin.ojuser.service.ThumbAndFavourService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 12:13
 */
@Service
public class ThumbAndFavourServiceImpl extends ServiceImpl<ThumbAndFavourMapper, ThumbAndFavour> implements ThumbAndFavourService {
    @Autowired
    private ThumbAndFavourMapper thumbAndFavourMapper;
    @Override
    public boolean thumb(boolean isThumb, String sourceId, Integer type) {
        Long userId = GetLoginUserUtils.getUserId();
        // 先判断是否点过赞
        ThumbAndFavour selected = getThumbAndFavourById(sourceId, userId, type);
        // 添加过这个点赞的记录
        LambdaQueryWrapper<ThumbAndFavour> wrapper = getThumbAndFavourLambdaQueryWrapper(sourceId, userId, type);
        if(selected != null){
            selected.setIsThumb(isThumb);
            thumbAndFavourMapper.update(selected, wrapper);
            return true;

        }
        else {
            ThumbAndFavour newOne = new ThumbAndFavour(userId, sourceId, type, isThumb, false);
            thumbAndFavourMapper.insert(newOne);
            return false;
        }

    }

    @Override
    public boolean favour(boolean isFavour, String sourceId, Integer type) {
        Long userId = GetLoginUserUtils.getUserId();
        // 先判断是否点过赞
        ThumbAndFavour selected = getThumbAndFavourById(sourceId, userId, type);
        LambdaQueryWrapper<ThumbAndFavour> wrapper = getThumbAndFavourLambdaQueryWrapper(sourceId, userId, type);

        // 添加过这个点赞的记录

        if(selected != null){
            selected.setIsFavour(isFavour);
            thumbAndFavourMapper.update(selected, wrapper);
            return true;

        }
        else {
            ThumbAndFavour newOne = new ThumbAndFavour(userId, sourceId, type, false, isFavour);
            thumbAndFavourMapper.insert(newOne);
            return false;
        }
    }

    @Override
    public Integer judgeDecrOrAdd(boolean isThumb, int i, String sourceId, Integer type) {
        Long userId = GetLoginUserUtils.getUserId();

        ThumbAndFavour favour = getThumbAndFavour(sourceId, userId, type);
        if(favour == null) return isThumb ? 1 : 0;
        if(i == 0){
            if(favour.getIsThumb() == isThumb) return 0;
            else if(!favour.getIsThumb() && isThumb) return 1;
            else return -1;
        }
        else{
            if(favour.getIsFavour() == isThumb) return 0;
            else if(!favour.getIsFavour() && isThumb) return 1;
            else return -1;
        }
    }

    @Override
    public QuestionShowVo getThumbAndFavourByUserId(String id, Long userId, String token) {
        LambdaQueryWrapper<ThumbAndFavour> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThumbAndFavour::getUserId, userId);
        wrapper.eq(ThumbAndFavour::getSourceId, id);
        wrapper.eq(ThumbAndFavour::getType, 1);
        ThumbAndFavour favour = thumbAndFavourMapper.selectOne(wrapper);
        QuestionShowVo questionShowVo = new QuestionShowVo();
        if(favour != null)
            BeanUtils.copyProperties(favour, questionShowVo);
        else {
            questionShowVo.setIsFavour(false);
            questionShowVo.setIsThumb(false);
        }
        return questionShowVo;
    }

    private ThumbAndFavour getThumbAndFavourById(String sourceId, Long userId, Integer type ) {
        ThumbAndFavour selected = getThumbAndFavour(sourceId, userId, type);
        return selected;
    }

    private ThumbAndFavour getThumbAndFavour(String sourceId, Long userId, Integer type) {
        LambdaQueryWrapper<ThumbAndFavour> wrapper = getThumbAndFavourLambdaQueryWrapper(sourceId, userId, type);
        ThumbAndFavour selected = thumbAndFavourMapper.selectOne(wrapper);
        return selected;
    }

    private static LambdaQueryWrapper<ThumbAndFavour> getThumbAndFavourLambdaQueryWrapper(String sourceId, Long userId, Integer type) {
        LambdaQueryWrapper<ThumbAndFavour> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThumbAndFavour::getUserId, userId);
        wrapper.eq(ThumbAndFavour::getSourceId, sourceId);
        wrapper.eq(ThumbAndFavour::getType, type);
        return wrapper;
    }
}
