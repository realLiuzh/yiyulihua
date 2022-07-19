package com.yiyulihua.gateway.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserJwtVo {
    private int id;
    private String name;
    private String permissions;

    /**
     * token 的过期时间
     */
    private Long exp;

    public UserJwtVo(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
