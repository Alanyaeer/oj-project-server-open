package com.genshin.ojarticle.mq;

import com.genshin.ojarticle.service.ArticleService;
import com.genshin.ojcommon.constants.MqConstants;
import com.genshin.ojcommon.domain.dto.mq.MqDto;
import com.genshin.ojcommon.domain.entity.Article;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 吴嘉豪
 * @date 2024/1/10 0:10
 */
@Component
public class ArticleListener {
    @Autowired
    private ArticleService articleService;

    @RabbitListener(queues = MqConstants.ARTICLE_CF_QUEUE)
    public void listenCfAdd(MqDto mqDto){
        Long id = mqDto.getId();
        Integer add = mqDto.getAdd();
        Integer addStatus = mqDto.getAddStatus();
        Integer articleType = mqDto.getArticleType();
        // 点赞
        if(addStatus == 0){
            if(add == -1){
                articleService.update().setSql("'thumb_num' = 'thumb_num' - 1").eq("id", id).eq("article_type", articleType).update();
            }
            else if(add == 1){
                articleService.update().setSql("'thumb_num' = 'thumb_num' + 1").eq("id", id).eq("article_type", articleType).update();

            }
        }
        else{
            // 收藏

            if(add == -1){
                articleService.update().setSql("'favour_num' = 'favour_num'- 1").eq("id", id).eq("article_type", articleType).update();
            }
            else if(add == 1){
                articleService.update().setSql("'favour_num' = 'favour_num' + 1").eq("id", id).eq("article_type", articleType).update();

            }
        }
    }
}
