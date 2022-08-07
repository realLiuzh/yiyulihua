package com.yiyulihua.common.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * 用户信息类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginTo {
    private Integer id;
    private String username;
    private String password;
    private List<String> permissionList;
    private String avatar;
    private String email;
    private String phone;
    private String organization;
}