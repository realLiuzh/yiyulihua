package com.yiyulihua.common.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-30 10:38:30
 */
@Data
@TableName("tb_message")
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 123L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("发送方用户id")
    private String sendUserId;

    @ApiModelProperty("接收方用户id")
    private String receiveUserId;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("是否为系统消息")
    private Integer isSystem;

    @ApiModelProperty("是否为离线消息")
    private Integer isOffline;

    @ApiModelProperty("发送方是否可见")
    private Integer sendUserVisible;

    @ApiModelProperty("接收方是否可见")
    private Integer receiveUserVisible;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("逻辑删除(0:有效)")
    @TableLogic
    private Integer isValid;

}
