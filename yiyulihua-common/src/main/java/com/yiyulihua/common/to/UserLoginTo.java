package com.yiyulihua.common.to;

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
}