package com.yiyulihua.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.yiyulihua.auth.dao.LoginDao;
import com.yiyulihua.auth.service.LoginService;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.to.LoginPasswordTo;
import com.yiyulihua.common.to.UserLoginTo;
import com.yiyulihua.common.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户管理业务类
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;

    @Override
    public SaTokenInfo login(LoginPasswordTo userInfo) {
        SaTokenInfo saTokenInfo = null;
        UserLoginTo userLoginTo = loadUserByUsername(userInfo.getEmail());
        AssertUtil.isTrue((userLoginTo == null || (!SaSecureUtil.md5(userInfo.getPassword()).equals(userLoginTo.getPassword()))),
                new ApiException(ApiExceptionEnum.LOGIN_ERROR));
        // 密码校验成功后登录，一行代码实现登录
        StpUtil.login(userLoginTo.getId());
        // 将用户信息存储到Session中
        StpUtil.getSession().set("userInfo", userLoginTo);
        // 获取当前登录用户Token信息
        saTokenInfo = StpUtil.getTokenInfo();
        return saTokenInfo;
    }

    private UserLoginTo loadUserByUsername(String username) {
        return loginDao.loadUserByUsername(username);
    }


}