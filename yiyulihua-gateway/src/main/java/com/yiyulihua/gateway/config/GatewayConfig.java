package com.yiyulihua.gateway.config;

import com.yiyulihua.gateway.filter.LogGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 网关配置
@Configuration
public class GatewayConfig {

    @Bean
    public LogGatewayFilterFactory logGatewayFilterFactory() {
        return new LogGatewayFilterFactory();
    }
}
