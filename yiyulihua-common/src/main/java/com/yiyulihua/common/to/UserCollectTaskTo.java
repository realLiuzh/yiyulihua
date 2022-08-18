package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户收藏任务实体类")
public class UserCollectTaskTo {
    @ApiModelProperty(value = "任务id",required = true)
    private Integer taskId;
}
