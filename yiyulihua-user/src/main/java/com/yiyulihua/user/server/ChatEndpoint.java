package com.yiyulihua.user.server;

import com.alibaba.fastjson.JSON;
import com.yiyulihua.common.vo.MessageVo;
import com.yiyulihua.user.util.ResultMessageUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunbo
 * @date 2022-07-27-17:12
 */
@ServerEndpoint("/chat")
@Component
public class ChatEndpoint {
    /**
     * 每一个客户端都有一个endpoint对象,用集合用来存储每一个客户端对象对用的endpoint
     * userId为key,endpoint为value
     */
    private static final ConcurrentHashMap<String, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    /**
     * 通过该session可以发送消息给指定客户端
     * 每一个endpoint对需要一个session
     */
    private Session session;

    /**
     * 用户 id ,用来标识用户
     */
    private String userId;

    /**
     * 连接建立时被调用
     */
    @OnOpen

    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        //TODO 给 userId 赋值
        //存储到容器中
        onlineUsers.put(userId, this);
    }

    /**
     * 接收到客户端数据时被调用
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        MessageVo messageVo = JSON.parseObject(message, MessageVo.class);
        //发送给 toUserId
        String toUserId = messageVo.getToUserId();
        //发送内容
        String context = messageVo.getMessage();


        //查看用户是否在线
        ChatEndpoint endpoint = onlineUsers.get(toUserId);
        if (endpoint != null) {
            String msg = ResultMessageUtils.toMessage(false, this.userId, context);
            // 发送数据
            endpoint.session.getBasicRemote().sendText(msg);
            //TODO 存数据库
        } else {
            //TODO 用户不在线的情况
        }
    }

    /**
     * 连接关闭时被调用
     */
    @OnClose
    public void onClose(Session session) {
        onlineUsers.remove(userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("出错了...");
    }

    // 发送系统广播消息
    public void broadcastMessage(String message) throws IOException {
        for (ChatEndpoint chat : onlineUsers.values()) {
            chat.session.getBasicRemote().sendText(message);
            //TODO 入库
        }

        //TODO 处理离线用户
    }

}
