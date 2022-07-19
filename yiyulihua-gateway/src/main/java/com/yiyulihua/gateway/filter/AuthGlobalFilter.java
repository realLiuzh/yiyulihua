package com.yiyulihua.gateway.filter;

import com.yiyulihua.gateway.constant.AuthConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

// 认证-全局过滤器
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    // 白名单
    public static final String NO_AUTH_PATH = "/auth/password/login,/auth/oauth/check_token,/auth/oauth/token";

    @Autowired
    private RedisMethod redisMethod;

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取request对象
        ServerHttpRequest request = exchange.getRequest();
        // 获取response对象
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        // 未登录 判断是否为白名单请求
        if (isAllowRequester(request)) {
            return chain.filter(exchange);
        }
        try {
            // 获取header中的token
            List<String> tokenList = request.getHeaders().get(AuthConstant.TOKEN);
            if (CollectionUtils.isEmpty(tokenList)) {
                // 拦截请求
                return getVoidMono(serverHttpResponse, );
            }
            String token = tokenList.get(0);
            // 验证token
            String redisToken = redisMethod.getString(token);
            if (StringUtils.isEmpty(redisToken)) {
                return getVoidMono(serverHttpResponse, );
            }

            // refresh token
            long time = redisMethod.getTime(token);
            if (time < 50) {
                AuthToken authToken = authService.refreshToken(token);
                if (!org.springframework.util.StringUtils.isEmpty(authToken)) {
                    String jsonString = JSON.toJSONString(authToken);
                    //删除Redis原有的令牌 并存入新的令牌
                    if (redisMethod.delString(token)) {
                        redisMethod.setStringTime(authToken.getAccess_token(), jsonString, ExpireTime);
                        return returnsToken(serverHttpResponse, authToken);
                    }
                }

            }
            UserJwtVo userJwtFromHeader = JwtUtils.getUserJwtFromToken(token);
            log.info("用户{}正在访问资源:{}", userJwtFromHeader.getName(), request.getPath());
            //权限判断 校验通过,请求头增强，放行
//            Map map = JwtUtils.parsingJwt(token);
//            String authorities = map.get("authorities").toString();
//            System.out.println(authorities);
//            if (!authorities.contains(path)) {
//                return getVoidMono(serverHttpResponse, ResponseCodeEnum.REFRESH_TOKEN_QUANXIANNOT);
//            }
            //增强请求头
            request.mutate().header(Authorization, "Bearer " + token, "token", token);
            //放行
            return chain.filter(exchange);
        } catch (Exception e) {
            log.info("服务解析用户信息失败:", e);
            //内部异常 返回500
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
    }

    /**
     * 创建response返回提示信息
     *
     * @param serverHttpResponse
     * @param responseCodeEnum
     * @return
     */
    private Mono<Void> getVoidMono(ServerHttpResponse serverHttpResponse, ResponseCodeEnum responseCodeEnum) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ResponseResult responseResult = ResponseResult.error(responseCodeEnum.getCode(), responseCodeEnum.getMessage());
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(responseResult).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

    /**
     * 创建response返回刷新令牌
     *
     * @param serverHttpResponse
     * @param o
     * @return
     */
    private Mono<Void> returnsToken(ServerHttpResponse serverHttpResponse, Object o) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(o).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

    /**
     * 判断请求是否在白名单
     *
     * @param request
     * @return
     */
    private boolean isAllowRequesr(ServerHttpRequest request) {
        //获取当前请求的path和method
        String path = request.getPath().toString();
        String method = request.getMethodValue();
        //判断是否允许
        if (StringUtils.startsWith(NO_AUTH_PATH, path)) {
            //是许可的路径 放行
            return true;
        }
        //不是白名单请求
        return false;
    }


    @Override
    public int getOrder() {
        return -2;
    }
}
