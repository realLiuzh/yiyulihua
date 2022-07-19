package com.yiyulihua.gateway.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient() {
        //配置当前redis要连接的信息
        Config config = new Config();
        config.useSingleServer().setAddress("redis://47.96.86.132:6379").setDatabase(1);
        return Redisson.create(config);
    }
}
