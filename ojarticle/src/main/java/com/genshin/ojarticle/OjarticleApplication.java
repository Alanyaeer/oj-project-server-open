package com.genshin.ojarticle;

import com.genshin.ojapi.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.genshin")
@MapperScan("com.genshin.ojarticle.mapper")
@EnableCaching
@EnableFeignClients(basePackages = "com.genshin.ojapi.client", defaultConfiguration = DefaultFeignConfig.class)
public class OjarticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjarticleApplication.class, args);
    }

}
