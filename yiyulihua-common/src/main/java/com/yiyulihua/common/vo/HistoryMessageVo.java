package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sunbo
 * @since 2022/08/20 16:44
 */
@ApiModel(value = "HistoryMessageVo", description = "历史消息对象")
@Data
public class HistoryMessageVo {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("发送方用户 id")
    private Integer fromUserId;

    @ApiModelProperty("接收方用户 id")
    private Integer receiveUserId;

    @ApiModelProperty("信息内容")
    private Object content;

    @ApiModelProperty("发送时间")
    private Date sendTime;
}
