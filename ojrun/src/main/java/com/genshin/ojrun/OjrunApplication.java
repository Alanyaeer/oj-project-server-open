package com.genshin.ojrun;

import com.genshin.ojapi.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.genshin")
@MapperScan("com.genshin.ojrun.mapper")
@EnableFeignClients(basePackages = "com.genshin.ojapi.client", defaultConfiguration = DefaultFeignConfig.class)

public class OjrunApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjrunApplication.class, args);
    }

}
