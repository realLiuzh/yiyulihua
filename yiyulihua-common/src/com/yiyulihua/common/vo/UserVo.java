package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ApiModel
public class UserVo implements Serializable {
    private static final long serialVersionUID = 7L;
    /**
     * 主键
     */
    @ApiModelProperty("唯一标识")
    private Integer id;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 头像
     */
    @ApiModelProperty("用户头像url")
    private String avatar;
    /**
     * 企业/学校
     */
    @ApiModelProperty("企业/学校")
    private String organization;
}
