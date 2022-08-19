package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


@ApiModel("用户登录对象")
public class LoginCodeTo {
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}