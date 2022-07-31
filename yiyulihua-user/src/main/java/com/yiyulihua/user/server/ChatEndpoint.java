package com.yiyulihua.user.server;

import com.alibaba.fastjson.JSON;
import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.vo.MessageVo;
import com.yiyulihua.common.vo.ResultMessageVo;
import com.yiyulihua.user.service.MessageService;
import com.yiyulihua.user.service.UserService;
import com.yiyulihua.user.util.ResultMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunbo
 * @date 2022-07-27-17:12
 */
@ServerEndpoint("/chat")
@Component
public class ChatEndpoint {

    private final MessageService messageService;

    private final UserService userService;

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


    @Autowired
    public ChatEndpoint(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    /**
     * 连接建立时被调用
     */
    @OnOpen

    public void onOpen(Session session, EndpointConfig config) throws IOException {
        this.session = session;
        //TODO 给 userId 赋值
        //存储到容器中
        onlineUsers.put(userId, this);

        //查询离线消息并发送
        List<ResultMessageVo> offlineMessage = messageService.getOfflineMessage(this.userId);
        if (offlineMessage.size() != 0) {
            for (ResultMessageVo vo : offlineMessage) {
                this.session.getBasicRemote().sendText(JSON.toJSONString(vo));
            }
            // 消除用户所有离线消息
            messageService.updateOfflineStatus(this.userId);
        }

    }

    /**
     * 接收到客户端数据时被调用
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        MessageVo messageVo = JSON.parseObject(message, MessageVo.class);
        //系统消息或用户消息
        if (messageVo.isSystemMsg()) {
            broadcastMessage(messageVo);
        } else {
            sendMessage(messageVo);
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


    public void sendMessage(MessageVo messageVo) throws IOException {
        //发送给 toUserId
        String toUserId = messageVo.getToUserId();

        // 存数据库
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSendUserId(this.userId);
        messageEntity.setReceiveUserId(toUserId);
        messageEntity.setContent(messageVo.getMessage());

        //查看用户是否在线
        ChatEndpoint endpoint = onlineUsers.get(toUserId);
        if (endpoint != null) {
            String msg = ResultMessageUtils.toMessage(this.userId, messageVo);
            // 发送数据
            endpoint.session.getBasicRemote().sendText(msg);
        } else {
            //用户不在线,将消息设为离线消息
            messageEntity.setIsOffline(1);
        }
        messageService.save(messageEntity);
    }

    // 发送系统广播消息
    public void broadcastMessage(MessageVo messageVo) throws IOException {
        // 如果是给单个用户的系统消息
        if (!StringUtils.isEmpty(messageVo.getToUserId())) {
            // 存数据库
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setIsSystem(1);
            messageEntity.setSendUserId(this.userId);
            messageEntity.setReceiveUserId(messageVo.getToUserId());
            messageEntity.setContent(messageVo.getMessage());
            messageEntity.setCreateTime(messageVo.getSendTime());

            ChatEndpoint chatEndpoint = onlineUsers.get(messageVo.getToUserId());
            if (chatEndpoint != null) {
                chatEndpoint.session.getBasicRemote().sendText(ResultMessageUtils.toMessage(this.userId, messageVo));
            } else {
                messageEntity.setIsOffline(1);
            }
            messageService.save(messageEntity);
        } else {
            //获得所有用户 id
            List<Integer> Ids = userService.getAllUserId();

            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setIsSystem(1);
            messageEntity.setSendUserId(this.userId);
            messageEntity.setContent(messageVo.getMessage());
            messageEntity.setCreateTime(messageVo.getSendTime());
            for (Integer id : Ids) {
                ChatEndpoint chat;
                //在线用户
                if ((chat = onlineUsers.get(id.toString())) != null) {
                    String msgResult = ResultMessageUtils.toMessage(this.userId, messageVo);
                    chat.session.getBasicRemote().sendText(msgResult);
                } else {
                    //设为离线消息
                    messageEntity.setIsOffline(1);
                }
                messageEntity.setReceiveUserId(id.toString());
                messageService.save(messageEntity);
            }
        }
    }

}
