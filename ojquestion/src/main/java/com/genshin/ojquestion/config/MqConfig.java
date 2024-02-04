package com.genshin.ojquestion.config;

import com.genshin.ojcommon.constants.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 吴嘉豪
 * @date 2024/1/10 0:05
 */
@Configuration
public class MqConfig {
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(MqConstants.QUESTION_EXCHANGE,true, true);
    }
    @Bean
    public Queue updateCfQueue (){
        return new Queue(MqConstants.QUESTION_CF_QUEUE,true);
    }
    @Bean
    public Binding updateCfQueueBinding(){
        return BindingBuilder.bind(updateCfQueue()).to(topicExchange()).with(MqConstants.QUESTION_CF_KEY);
    }
}
