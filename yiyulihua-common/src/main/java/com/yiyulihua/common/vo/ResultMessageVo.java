package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 服务器返回给客户端的数据
 *
 * @author sunbo
 * @date 2022-07-27-18:00
 */
@ApiModel(value = "ResultMessageVo", description = "返回消息对象")
@Data
public class ResultMessageVo {

    @ApiModelProperty("消息id")
    private Integer id;

    @ApiModelProperty("是否为系统消息0:false 1:true")
    private Integer isSystem;

    @ApiModelProperty("发送方用户 id")
    private Integer fromUserId;

    @ApiModelProperty("接收方用户 id")
    private Integer receiveUserId;

    @ApiModelProperty("信息内容")
    private Object content;

    @ApiModelProperty("发送时间")
    private Date sendTime;
}
