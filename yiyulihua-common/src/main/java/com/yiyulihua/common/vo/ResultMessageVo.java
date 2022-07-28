package com.yiyulihua.common.vo;

import lombok.Data;

/**
 * 服务器返回给客户端的数据
 *
 * @author sunbo
 * @date 2022-07-27-18:00
 */
@Data
public class ResultMessageVo {
    /**
     * 是否为系统消息
     */
    private boolean isSystem;
    /**
     * 发送用户 id
     */
    private String fromUserId;
    /**
     * 信息内容
     */
    private Object message;
}
