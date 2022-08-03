package com.yiyulihua.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.yiyulihua.auth.service.LoginService;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.ApiToken;
import com.yiyulihua.common.to.LoginPasswordTo;
import com.yiyulihua.common.to.LoginRegisterTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 */
@RestController
@Api(value = "LoginController", tags = {"认证访问接口"})
public class LoginController {

    @Autowired
    private LoginService loginService;


    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<ApiToken> login(@RequestBody LoginPasswordTo userInfo) {
        SaTokenInfo saTokenInfo = loginService.login(userInfo);
        AssertUtil.isTrue(saTokenInfo == null, new ApiException(ApiExceptionEnum.LOGIN_ERROR));
        return new Result<ApiToken>().setData(new ApiToken(saTokenInfo.getTokenName(), saTokenInfo.getTokenValue()));
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<Object> register(@RequestBody LoginRegisterTo userInfo) {
        loginService.register(userInfo);
        return Result.success();
    }

}
