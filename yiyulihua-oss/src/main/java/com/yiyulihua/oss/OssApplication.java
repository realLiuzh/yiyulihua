package com.yiyulihua.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sunbo
 * @since  2022-07-18-17:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.yiyulihua"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
