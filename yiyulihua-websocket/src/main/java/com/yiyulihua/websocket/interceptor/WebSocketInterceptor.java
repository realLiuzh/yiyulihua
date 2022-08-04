package com.yiyulihua.websocket.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author sunbo
 * @date 2022/08/03 22:06
 */

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        ServletServerHttpResponse serverHttpResponse = (ServletServerHttpResponse) response;

        //获取tokenValue
        String tokenValue = serverHttpRequest.getServletRequest().getHeader("Sec-WebSocket-Protocol");
        //在后端握手时设置一下请求头（Sec-WebSocket-Protocol），前端发来什么授权值，这里就设置什么值，不设置会报错导致建立连接成功后立即被关闭
        serverHttpResponse.getServletResponse().setHeader("Sec-WebSocket-Protocol", tokenValue);

        //这里对获取到的 authorization 授权码进行业务校验如 jwt 校验
        Object loginId = StpUtil.getLoginIdByToken(tokenValue);
        if (loginId == null) {
            throw new ApiException(ApiExceptionEnum.SIGNATURE_NOT_MATCH);
        } else {
            //放入attribute
            map.put("loginId", loginId);
            return true;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    }
}
