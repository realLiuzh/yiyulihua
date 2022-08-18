package com.yiyulihua.common.to;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Api("任务提交对象")
public class TaskCommitTo {

    @ApiModelProperty(value = "任务id", required = true)
    private Integer taskId;
    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId;
    @ApiModelProperty(value = "作品url", required = true)
    private String url;
}
