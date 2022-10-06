package com.yiyulihua.websocket.server;

import com.alibaba.fastjson.JSON;
import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.to.MessageTo;
import com.yiyulihua.common.vo.ResultMessageVo;
import com.yiyulihua.websocket.feign.MessageAndUserFeignClient;
import com.yiyulihua.websocket.util.ResultMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunbo
 * @since 2022/08/03 21:09
 */

public class WebSocketServer extends TextWebSocketHandler {

    private MessageAndUserFeignClient messageAndUserFeignClient;

    private static final ConcurrentHashMap<Integer, WebSocketSession> onlineUsers = new ConcurrentHashMap<Integer, WebSocketSession>();


    @Autowired
    public void setMessageAndUserFeignClient(MessageAndUserFeignClient messageAndUserFeignClient) {
        this.messageAndUserFeignClient = messageAndUserFeignClient;
    }

    /**
     * 连接开启
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //获得 userId
        Integer userId = Integer.valueOf(session.getAttributes().get("loginId").toString());

        //存储到容器中
        onlineUsers.put(userId, session);

        //查询离线消息并发送
        List<ResultMessageVo> offlineMessage = messageAndUserFeignClient.getOfflineMessage(userId);
        if (offlineMessage != null && offlineMessage.size() != 0) {
            for (ResultMessageVo vo : offlineMessage) {
                session.sendMessage(new TextMessage(JSON.toJSONStringWithDateFormat(vo, "yyyy-MM-dd HH:mm:ss")));
            }
            // 消除用户所有离线消息
            messageAndUserFeignClient.updateOfflineMessageStatus(userId);
        }
    }

    /**
     * 收到消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        Integer userId = Integer.valueOf(session.getAttributes().get("loginId").toString());

        MessageTo messageTo = JSON.parseObject(message.getPayload(), MessageTo.class);
        //发送用户消息
        sendMessage(userId, messageTo);
    }

    /**
     * 连接关闭
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer userId = Integer.valueOf(session.getAttributes().get("loginId").toString());
        onlineUsers.remove(userId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Integer userId = Integer.valueOf(session.getAttributes().get("loginId").toString());
        onlineUsers.remove(userId);
    }

    public void sendMessage(Integer userId, MessageTo messageTo) throws IOException {
        //发送给 toUserId
        Integer toUserId = messageTo.getToUserId();

        // 存数据库
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSendUserId(userId);
        messageEntity.setReceiveUserId(toUserId);
        messageEntity.setContent(messageTo.getContent());
        messageEntity.setCreateTime(messageTo.getSendTime());

        //查看用户是否在线
        WebSocketSession session = onlineUsers.get(toUserId);
        if (session != null) {
            Integer id = messageAndUserFeignClient.save(messageEntity);
            String msg = ResultMessageUtils.toMessage(id, userId, messageTo);
            // 发送数据
            session.sendMessage(new TextMessage(msg));
        } else {
            //用户不在线,将消息设为离线消息
            messageEntity.setIsOffline(1);
            messageAndUserFeignClient.save(messageEntity);
        }

    }
/*
    // 发送系统广播消息
    public void broadcastMessage(Integer userId, MessageTo messageTo) throws IOException {



        // 如果是给单个用户的系统消息
        if (!StringUtils.isEmpty(messageTo.getToUserId())) {
            // 存数据库
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setIsSystem(1);
            messageEntity.setSendUserId(userId);
            messageEntity.setReceiveUserId(messageTo.getToUserId());
            messageEntity.setContent(messageTo.getContent());
            messageEntity.setCreateTime(messageTo.getSendTime());

            WebSocketSession session = onlineUsers.get(messageTo.getToUserId());
            if (session != null) {
                Integer id = messageAndUserFeignClient.save(messageEntity);

                session.sendMessage(new TextMessage(ResultMessageUtils.toMessage(id, userId, messageTo)));
            } else {
                messageEntity.setIsOffline(1);
                messageAndUserFeignClient.save(messageEntity);
            }
        } else {

            //获得所有用户 id
            List<Integer> Ids = messageAndUserFeignClient.getAllUserId();

            for (Integer id : Ids) {
                if (userId.equals(id)) {
                    continue;
                }

                MessageEntity messageEntity = new MessageEntity();

                messageEntity.setIsSystem(1);
                messageEntity.setSendUserId(userId);
                messageEntity.setContent(messageTo.getContent());
                messageEntity.setCreateTime(messageTo.getSendTime());
                messageEntity.setReceiveUserId(id);

                WebSocketSession session;
                //在线用户
                if ((session = onlineUsers.get(id)) != null) {
                    Integer mId = messageAndUserFeignClient.save(messageEntity);

                    String msgResult = ResultMessageUtils.toMessage(mId, userId, messageTo);
                    session.sendMessage(new TextMessage(msgResult));
                } else {
                    //设为离线消息
                    messageEntity.setIsOffline(1);
                    messageAndUserFeignClient.save(messageEntity);
                }

            }
        }
    }*/
}
