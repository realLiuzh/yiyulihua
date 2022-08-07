package com.yiyulihua.common.vo;

import com.yiyulihua.common.to.ApiToken;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户认证成功实体类")
public class UserLoginVo {
    @ApiModelProperty(value = "主键", required = true)
    private Integer id;
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "头像url", required = true)
    private String avatar;
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    @ApiModelProperty(value = "企业/学校", required = true)
    private String organization;
    @ApiModelProperty(value = "token", required = true)
    private ApiToken token;
}
