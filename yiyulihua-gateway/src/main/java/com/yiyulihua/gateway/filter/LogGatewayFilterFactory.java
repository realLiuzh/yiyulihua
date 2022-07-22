package com.yiyulihua.gateway.filter;

import com.yiyulihua.gateway.util.CusAccessObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;


// 记录接口花费时间
@Slf4j
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {
    private static final String REQUEST_START_TIME = "request_start_time";


    public LogGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            exchange.getAttributes().put(REQUEST_START_TIME, System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        Long startTime = exchange.getAttribute(REQUEST_START_TIME);
                        if (startTime != null) {
                            ServerHttpRequest request = exchange.getRequest();
                            log.info("源ip:{},目的地址:{},消耗时间:{}s", CusAccessObjectUtil.getIpAddress(request), request.getURI(), (System.currentTimeMillis() - startTime) / 1000.0);
                        }
                    })
            );
        });
    }


    public static class Config {

    }
}
