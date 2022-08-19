package com.yiyulihua.auth.controller;

import com.yiyulihua.auth.service.LoginService;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.ForgetPasswordTo;
import com.yiyulihua.common.to.LoginCodeTo;
import com.yiyulihua.common.to.LoginPasswordTo;
import com.yiyulihua.common.to.LoginRegisterTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.vo.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 自定义Oauth2获取令牌接口
 */
@RestController
@Api(value = "LoginController", tags = {"认证访问接口"})
public class LoginController {

    @Autowired
    private LoginService loginService;


    @ApiOperation("用户邮箱密码登录")
    @PostMapping("/password/login")
    public Result<UserLoginVo> loginByPassword(@RequestBody LoginPasswordTo userInfo) {
        UserLoginVo userLoginVo = loginService.loginByPassword(userInfo);
        AssertUtil.isTrue(userLoginVo == null, new ApiException(ApiExceptionEnum.LOGIN_ERROR));
        return new Result<UserLoginVo>().setData(userLoginVo);
    }

    @ApiOperation("用户手机验证码登录")
    @PostMapping("/code/login")
    public Result<UserLoginVo> loginByCode(@RequestBody LoginCodeTo userInfo) {
        UserLoginVo userLoginVo = loginService.loginByCode(userInfo);
        AssertUtil.isTrue(userLoginVo == null, new ApiException(ApiExceptionEnum.LOGIN_ERROR));
        return new Result<UserLoginVo>().setData(userLoginVo);
    }


    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<UserLoginVo> register(@RequestBody LoginRegisterTo userInfo) {
        UserLoginVo userLoginVo = loginService.register(userInfo);
        return new Result<UserLoginVo>().setData(userLoginVo);
    }

    @ApiOperation("忘记密码")
    @PostMapping("/forget")
    public Result<Object> forgetPassword(@RequestBody ForgetPasswordTo forgetPasswordTo) {
        loginService.forgetPassword(forgetPasswordTo);
        return Result.success();
    }

}
