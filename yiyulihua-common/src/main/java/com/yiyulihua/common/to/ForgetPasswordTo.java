package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


@ApiModel("忘记密码实体类")
public class ForgetPasswordTo {
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
    @ApiModelProperty(value = "新密码",required = true)
    private String password;
}