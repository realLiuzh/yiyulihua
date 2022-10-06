package com.yiyulihua.common.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

/**
 * @author sunbo
 * @since 2022/08/20 20:41
 */
@ApiModel(value = "HistoryMessageTo", description = "获取历史记录对象")
@Data
@Validated
public class HistoryMessageTo {

    @ApiModelProperty(value = "当前页", required = true)
    private Integer current;

    @ApiModelProperty(value = "每页记录数", required = true)
    private Integer size;

    @ApiModelProperty(value = "对方用户id", required = true)
    private Integer toUserId;

    @ApiModelProperty("开始时间,默认 2022-01-01 00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime begin = LocalDateTime.of(2022, 1, 1, 0, 0, 0);

    @ApiModelProperty("截止时间,默认当前时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end = LocalDateTime.now();
}
