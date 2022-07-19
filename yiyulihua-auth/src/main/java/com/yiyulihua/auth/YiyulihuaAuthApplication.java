package com.yiyulihua.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class YiyulihuaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiyulihuaAuthApplication.class, args);
    }

}
