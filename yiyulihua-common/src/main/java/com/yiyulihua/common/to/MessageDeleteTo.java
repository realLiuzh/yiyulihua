package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @date 2022-08-02-14:22
 */
@ApiModel(value = "MessageDeleteVo", description = "删除聊天记录对象")
@Data
public class MessageDeleteTo {
    @ApiModelProperty(value = "消息id",required = true)
    private String msgId;
    @ApiModelProperty(value = "用户身份(0: 发送方 1:接收方)",required = true)
    private Integer role;
}
