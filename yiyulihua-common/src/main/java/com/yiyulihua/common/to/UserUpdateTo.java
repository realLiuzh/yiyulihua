package com.yiyulihua.common.to;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel("用户信息修改实体类")
public class UserUpdateTo {
    @ApiModelProperty(value = "id", required = true)
    private Integer id;
    @ApiModelProperty(value = "用户头像url", required = false)
    private String avatar;
    @ApiModelProperty(value = "用户昵称", required = false)
    private String username;
    @ApiModelProperty(value = "企业/学校", required = false)
    private String organization;
    @ApiModelProperty(value = "密码", required = false)
    private String password;
    @ApiModelProperty(value = "手机号", required = false)
    private String phone;
    @ApiModelProperty(value = "手机验证码", required = false)
    private String code;
}
