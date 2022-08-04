package com.yiyulihua.websocket.config;

import com.yiyulihua.websocket.interceptor.WebSocketInterceptor;
import com.yiyulihua.websocket.server.WebSocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @author sunbo
 * @date 2022-07-27-17:10
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(webSocketServer(), "chat")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketServer webSocketServer() {
        return new WebSocketServer();
    }
}
