package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @since 2022-08-02-15:43
 */
@ApiModel(value = "MessageDeleteUserTo", description = "删除聊天记录对象")
@Data
public class MessageDeleteUserTo {
    @ApiModelProperty(value = "用户1 id(操作方)", required = true)
    private Integer userId;
    @ApiModelProperty(value = "用户2 id", required = true)
    private Integer toUserId;
}
