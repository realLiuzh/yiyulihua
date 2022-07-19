package com.yiyulihua.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.yiyulihua.gateway.config.RedisMethod;
import com.yiyulihua.gateway.constant.AuthConstant;
import com.yiyulihua.gateway.entity.UserJwtVo;
import com.yiyulihua.gateway.service.AuthService;
import com.yiyulihua.gateway.util.AssertUtil;
import com.yiyulihua.gateway.util.JwtUtils;
import com.yiyulihua.gateway.util.ResponseCodeEnum;
import com.yiyulihua.gateway.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

// 认证-全局过滤器
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    public static final String Authorization = "Authorization";

    // 白名单
    public static final List<String> NOAUTHPATH;

    static {
        NOAUTHPATH = new ArrayList<>();
        NOAUTHPATH.add("/api/user/info/1");
    }

    //在redis里面的过期时间 7200秒
    private final Long ExpireTime = 7200l;

    @Autowired
    private RedisMethod redisMethod;


    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取request对象
        ServerHttpRequest request = exchange.getRequest();
        // 获取response对象
        ServerHttpResponse response = exchange.getResponse();
        // 未登录 判断是否为白名单请求
        if (isAllowRequester(request)) return chain.filter(exchange);
        // 获取header中的token
        List<String> tokenList = request.getHeaders().get(AuthConstant.TOKEN);
        if (CollectionUtils.isEmpty(tokenList)) {
            return getVoidMono(response, ResponseCodeEnum.TOKEN_MISSION);
        }

        // 验证token
        String token = tokenList.get(0);
        String redisToken = redisMethod.getString(token);
        if (StringUtils.isEmpty(redisToken)) {
            return getVoidMono(response, ResponseCodeEnum.TOKEN_INVALID);
        }

        // TODO refresh token

        UserJwtVo userJwtVo = JwtUtils.getUserInfoByToken(token);
        log.info("用户:{}正在访问资源:{}", userJwtVo.getName(), request.getPath());
        // TODO 权限判断 校验通过,请求头增强，放行
        //增强请求头
        request.mutate().header(Authorization, "Bearer " + token, "token", token);

        return chain.filter(exchange);
    }

//    private void refreshToken(String token) {
//        long time = redisMethod.getTime(token);
//        if (time <= 3600) {
//            AuthToken authToken = authService.refresh_token(token);
//            if (!org.springframework.util.StringUtils.isEmpty(authToken)) {
//                String jsonString = JSON.toJSONString(authToken);
//                //删除Redis原有的令牌 并存入新的令牌
//                if (redisMethod.delString(token)) {
//                    redisMethod.setStringTime(authToken.getAccess_token(), jsonString, ExpireTime);
//                    return returnsToken(serverHttpResponse, authToken);
//                }
//            }
//
//        }
//    }


    // 创建response返回信息
    private Mono<Void> getVoidMono(ServerHttpResponse serverHttpResponse, ResponseCodeEnum responseCodeEnum) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ResponseResult<Object> responseResult = ResponseResult.error(responseCodeEnum.getCode(), responseCodeEnum.getMessage());
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(responseResult).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

    // 创建response返回刷新令牌
    private Mono<Void> returnsToken(ServerHttpResponse serverHttpResponse, Object o) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(o).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

    // 判断请求是否在白名单
    private boolean isAllowRequester(ServerHttpRequest request) {
        //获取当前请求的path和method
        String path = request.getPath().toString();
        //判断是否允许
        for (String s : NOAUTHPATH) {
            if (s.equals(path)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int getOrder() {
        return -2;
    }
}
