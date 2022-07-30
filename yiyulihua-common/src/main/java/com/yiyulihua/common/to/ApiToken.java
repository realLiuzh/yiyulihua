package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("token对象")
public class ApiToken {
    @ApiModelProperty("token-head")
    private String tokenHead;
    @ApiModelProperty("token-value")
    private String token;
}
