package com.yiyulihua.common.vo;

import lombok.Data;

import java.util.Date;

/**
 * 客户端发送给服务器的websocket数据
 *
 * @author sunbo
 * @date 2022-07-27-17:57
 */

@Data
public class MessageVo {
    /**
     * 是否为系统消息
     */
    private boolean isSystemMsg;
    /**
     * 接收方用户 id
     */
    private String toUserId;
    /**
     * 信息内容
     */
    private String message;
    /**
     * 发送时间
     */
    private Date sendTime;
}
