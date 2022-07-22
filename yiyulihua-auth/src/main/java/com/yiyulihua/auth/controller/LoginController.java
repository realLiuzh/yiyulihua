package com.yiyulihua.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.yiyulihua.auth.service.LoginService;
import com.yiyulihua.common.exception.CustomException;
import com.yiyulihua.common.exception.ExceptionEnum;
import com.yiyulihua.common.to.LoginPasswordTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(@RequestBody LoginPasswordTo userInfo) {
        SaTokenInfo saTokenInfo = loginService.login(userInfo);
        AssertUtil.isTrue(saTokenInfo == null, new CustomException(ExceptionEnum.LOGIN_ERROR));
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", saTokenInfo.getTokenValue());
        tokenMap.put("tokenHead", saTokenInfo.getTokenName());
        return R.ok(tokenMap);
    }
}
