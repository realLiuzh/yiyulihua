package com.yiyulihua.gateway.security;

import cn.dev33.satoken.reactor.context.SaReactorSyncHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

//@Configuration
public class SaTokenConfig {

    private static final String[] EXCLUDE_PATH_PATTERNS = {
            // Swagger
            "/**/swagger-ui.*",
            "/**/swagger-resources/**",
            "/**/webjars/**",
            "/**/v2/api-docs/**",
            "/swagger-ui.html/**",
            "/**/doc.html/**",
            "/error",
            "/favicon.ico",
            "sso/auth",
            "/csrf",


            "/api-auth/login",
            "/api-auth/send/phone"
    };

    /**
     * 注册Sa-Token全局过滤器
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")
                // 鉴权方法：每次访问进入
                .setAuth(r -> {
                    // 登录认证：除登录接口都需要认证
                    SaRouter.match(Collections.singletonList("/**"), Arrays.stream(EXCLUDE_PATH_PATTERNS).collect(Collectors.toList()), StpUtil::checkLogin);
                    // 权限认证：不同接口访问权限不同
//                    SaRouter.match("/api/test/hello", () -> StpUtil.checkPermission("api:test:hello"));
                })
                // setAuth方法异常处理
                .setError(e -> {
                    // 设置错误返回格式为JSON
                    ServerWebExchange exchange = SaReactorSyncHolder.getContent();
                    exchange.getResponse().getHeaders().set("Content-Type", "application/json; charset=utf-8");
                    return SaResult.error(e.getMessage());
                });
    }
}