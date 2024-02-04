package com.genshin.ojquestion.mq;

import com.genshin.ojcommon.constants.MqConstants;
import com.genshin.ojcommon.domain.dto.mq.MqDto;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojquestion.mapper.QuestionMapper;
import com.genshin.ojquestion.service.QuestionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 吴嘉豪
 * @date 2024/1/10 0:19
 */
@Component
public class QuestionListener {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionMapper questionMapper;
    @RabbitListener(queues = MqConstants.QUESTION_CF_QUEUE)
    public void listenCfAdd(MqDto mqDto) {
//        Integer addStatus = mqDto.getAddStatus();
        Integer add = mqDto.getAdd();
        Long id = mqDto.getId();
        Integer addStatus = mqDto.getAddStatus();
        if(addStatus == 0){
            if(add == -1){
                 questionMapper.updateLikes(id, -1);
//                questionService.update().setSql("'likes' = 'likes' - 1").eq("id", id).update();
            }
            else if(add == 1){
                questionMapper.updateLikes(id, 1);
//                questionService.update().setSql("'likes' = 'likes' + 1").eq("id", id).update();

            }
            System.out.println("nihao");
        }
        else{
            // 收藏

            if(add == -1){
                questionMapper.updateFavourNum(id, -1);
//                questionService.update().setSql("'favour_num' = 'favour_num' - 1").eq("id", id).update();
            }
            else if(add == 1){
                questionMapper.updateFavourNum(id, 1);
//                questionService.update().setSql("'favour_num' = 'favour_num' + 1").eq("id", id).update();

            }
        }
    }


}
