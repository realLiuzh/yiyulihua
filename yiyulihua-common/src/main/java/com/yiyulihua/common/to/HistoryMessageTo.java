package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sunbo
 * @since 2022/08/20 20:41
 */
@ApiModel(value = "HistoryMessageTo", description = "获取历史记录对象")
@Data
public class HistoryMessageTo {

    @ApiModelProperty(value = "当前页",required = true)
    private Integer current;

    @ApiModelProperty(value = "每页记录数",required = true)
    private Integer size;

    @ApiModelProperty(value = "对方用户id",required = true)
    private Integer toUserId;

    @ApiModelProperty("开始时间,默认 2022-01-01 00:00:00")
    private Date begin;

    @ApiModelProperty("截止时间,默认当前时间")
    private Date end;
}
