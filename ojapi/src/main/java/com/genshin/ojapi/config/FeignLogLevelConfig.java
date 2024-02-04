package com.genshin.ojapi.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
public class FeignLogLevelConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }
}
