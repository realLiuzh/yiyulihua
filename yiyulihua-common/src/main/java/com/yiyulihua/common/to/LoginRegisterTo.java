package com.yiyulihua.common.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@ApiModel("用户注册对象")
public class LoginRegisterTo {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private String username;
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
    @JsonIgnore
    private Integer isValid;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
}
