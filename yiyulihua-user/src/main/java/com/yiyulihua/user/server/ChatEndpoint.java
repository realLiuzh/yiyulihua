package com.yiyulihua.user.server;

import com.alibaba.fastjson.JSON;
import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.to.MessageTo;
import com.yiyulihua.common.vo.ResultMessageVo;
import com.yiyulihua.user.service.MessageService;
import com.yiyulihua.user.service.UserService;
import com.yiyulihua.user.util.ResultMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunbo
 * @date 2022-07-27-17:12
 */
@Slf4j
@ServerEndpoint("/chat")
@Component
public class ChatEndpoint {

    private static MessageService messageService;
    private static UserService userService;

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
    public void setMessageService(MessageService messageService) {
        ChatEndpoint.messageService = messageService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        ChatEndpoint.userService = userService;
    }

    /**
     * 连接建立时被调用
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        this.session = session;
        //TODO 给 userId 赋值
        Random random = new Random();
        int i = random.nextInt(1000) + 1000;
//        this.userId = String.valueOf(i);
        //存储到容器中
        this.userId = "1";
        onlineUsers.put(userId, this);

        //ceshu
        this.session.getBasicRemote().sendText(String.valueOf(i));

        //查询离线消息并发送
        List<ResultMessageVo> offlineMessage = messageService.getOfflineMessage(this.userId);
        if (offlineMessage != null && offlineMessage.size() != 0) {
            for (ResultMessageVo vo : offlineMessage) {
                this.session.getBasicRemote().sendText(JSON.toJSONStringWithDateFormat(vo, "yyyy-MM-dd HH:mm:ss"));
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
        MessageTo messageTo = JSON.parseObject(message, MessageTo.class);
        //系统消息或用户消息
        if (messageTo.isSystemMsg()) {
            broadcastMessage(messageTo);
        } else {
            sendMessage(messageTo);
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
    public void onError(Session session, Throwable throwable) throws Throwable {
        log.error(throwable.getMessage());
        throw throwable;
    }


    public void sendMessage(MessageTo messageTo) throws IOException {
        //发送给 toUserId
        String toUserId = messageTo.getToUserId();

        // 存数据库
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSendUserId(this.userId);
        messageEntity.setReceiveUserId(toUserId);
        messageEntity.setContent(messageTo.getMessage());
        messageEntity.setCreateTime(messageTo.getSendTime());

        //查看用户是否在线
        ChatEndpoint endpoint = onlineUsers.get(toUserId);
        if (endpoint != null) {
            String msg = ResultMessageUtils.toMessage(this.userId, messageTo);
            // 发送数据
            endpoint.session.getBasicRemote().sendText(msg);
        } else {
            //用户不在线,将消息设为离线消息
            messageEntity.setIsOffline(1);
        }
        messageService.save(messageEntity);
    }

    // 发送系统广播消息
    public void broadcastMessage(MessageTo messageTo) throws IOException {
        // 如果是给单个用户的系统消息
        if (!StringUtils.isEmpty(messageTo.getToUserId())) {
            // 存数据库
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setIsSystem(1);
            messageEntity.setSendUserId(this.userId);
            messageEntity.setReceiveUserId(messageTo.getToUserId());
            messageEntity.setContent(messageTo.getMessage());
            messageEntity.setCreateTime(messageTo.getSendTime());

            ChatEndpoint chatEndpoint = onlineUsers.get(messageTo.getToUserId());
            if (chatEndpoint != null) {
                chatEndpoint.session.getBasicRemote().sendText(ResultMessageUtils.toMessage(this.userId, messageTo));
            } else {
                messageEntity.setIsOffline(1);
            }
            messageService.save(messageEntity);
        } else {
            //获得所有用户 id
            List<Integer> Ids = userService.getAllUserId();

            for (Integer id : Ids) {
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setIsSystem(1);
                messageEntity.setSendUserId(this.userId);
                messageEntity.setContent(messageTo.getMessage());
                messageEntity.setCreateTime(messageTo.getSendTime());

                ChatEndpoint chat;
                //在线用户
                if ((chat = onlineUsers.get(id.toString())) != null) {
                    String msgResult = ResultMessageUtils.toMessage(this.userId, messageTo);
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
