package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


@ApiModel("邮箱密码登录对象")
public class LoginPasswordTo {
    @ApiModelProperty(value = "邮箱",required = true)
    private String email;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
