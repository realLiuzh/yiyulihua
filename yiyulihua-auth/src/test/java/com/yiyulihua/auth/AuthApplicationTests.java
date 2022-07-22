package com.yiyulihua.auth;

import cn.dev33.satoken.secure.SaSecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(SaSecureUtil.md5("123456"));
    }

}
