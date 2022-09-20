package com.yiyulihua.common.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author snbo
 * @since  2022-07-30 10:38:30
 */
@Data
@TableName("tb_message")
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 123L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("发送方用户id")
    private Integer sendUserId;

    @ApiModelProperty("接收方用户id")
    private Integer receiveUserId;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("是否为系统消息")
    private Integer isSystem = 0;

    @ApiModelProperty("是否为离线消息")
    private Integer isOffline = 0;

    @ApiModelProperty("发送方是否可见")
    private Integer sendUserVisible = 0;

    @ApiModelProperty("接收方是否可见")
    private Integer receiveUserVisible = 0;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("逻辑删除(0:有效)")
    @TableLogic
    private Integer isValid;

}
