package com.yiyulihua.works;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sunbo
 * @date 2022-07-16-17:20
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.yiyulihua"})
@EnableDiscoveryClient
public class WorksApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorksApplication.class, args);
    }
}
