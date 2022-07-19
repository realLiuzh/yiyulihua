package com.yiyulihua.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisMethod redisMethod;

    /**
     * 用户密码登录
     *
     * @param login
     * @return
     */
    @PostMapping("/password/login")
    public LoginResult login(@RequestBody LoginDTO login) {
        //判断用户名和密码不能为空
        if (StringUtils.isEmpty(login.getUserName()) || login.getUserName() == null) {
            return new LoginResult(AuthCode.AUTH_USERNAME_NONE, null, null);
        }
        if (StringUtils.isEmpty(login.getPassword()) || login.getPassword() == null) {
            return new LoginResult(AuthCode.AUTH_PASSWORD_NONE, null, null);
        }
        //拿着用户名和密码去请求服务来获取 token
        //登录成功显示这个
        AuthToken token = tokenService.passwordVerifyToken(login.getUserName(), login.getPassword());
        return this.errorInfo(token);
    }
}
