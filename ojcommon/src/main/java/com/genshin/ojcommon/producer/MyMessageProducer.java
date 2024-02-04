package com.genshin.ojcommon.producer;

import com.genshin.ojcommon.domain.dto.mq.SubmitQuestionWithTokenDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param exchange
     * @param routingKey
     * @param message
     */
    public void sendMessage(String exchange, String routingKey, SubmitQuestionWithTokenDto message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}