package com.genshin.ojrun.config;

import com.genshin.ojcommon.constants.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 吴嘉豪
 * @date 2024/1/16 12:01
 */
@Configuration
public class MqConfig {
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(MqConstants.RUN_EXCHANGE,true, true);
    }
    @Bean
    public Queue updateCfQueue (){
        return new Queue(MqConstants.RUN_CODE_QUEUE,true);
    }
    @Bean
    public Binding updateCfQueueBinding(){
        return BindingBuilder.bind(updateCfQueue()).to(topicExchange()).with(MqConstants.RUN_CODE_KEY);
    }
}
