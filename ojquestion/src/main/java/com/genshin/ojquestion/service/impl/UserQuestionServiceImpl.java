package com.genshin.ojquestion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojcommon.domain.entity.UserQuestion;
import com.genshin.ojquestion.mapper.QuestionMapper;
import com.genshin.ojquestion.mapper.UserQuestionMapper;
import com.genshin.ojquestion.service.UserQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 吴嘉豪
 * @date 2024/1/2 22:59
 */
@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestion> implements UserQuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserQuestionMapper userQuestionMapper;
    @Override
    public void insert(Long titleId, Long userId) {
        Integer score = questionMapper.selectById(titleId).getScore();
        if(score < 1600){
            update().setSql("solve_easy=solve_easy + 1").update();

        }
        else if (score < 2000){
            update().setSql("solve_middle=solve_middle + 1").update();

        }
        else {
            update().setSql("solve_hard=solve_hard + 1").update();

        }
    }
}
