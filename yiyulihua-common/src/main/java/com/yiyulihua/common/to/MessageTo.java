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

    @ApiModelProperty(value = "是否为系统消息0:false 1:true", required = true)
    private Integer isSystem;

    @ApiModelProperty(value = "接收方用户 id", required = true)
    private String toUserId;

    @ApiModelProperty(value = "消息内容", required = true)
    private String content;

    @ApiModelProperty(value = "消息发送时间", required = true)
    private Date sendTime;
}
