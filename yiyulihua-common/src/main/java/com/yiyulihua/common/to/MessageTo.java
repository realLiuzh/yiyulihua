package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 客户端发送给服务器的websocket数据
 *
 * @author sunbo
 * @date 2022-07-27-17:57
 */
@ApiModel(value = "MessageTo", description = "发送信息对象")
@Data
public class MessageTo {

    @ApiModelProperty("是否为系统消息")
    private boolean isSystemMsg;

    @ApiModelProperty("接收方用户 id")
    private String toUserId;

    @ApiModelProperty("消息内容")
    private String message;

    @ApiModelProperty("消息发送时间")
    private Date sendTime;
}
