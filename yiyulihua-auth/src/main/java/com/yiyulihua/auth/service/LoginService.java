package com.yiyulihua.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.yiyulihua.common.to.LoginPasswordTo;
import com.yiyulihua.common.to.LoginRegisterTo;

public interface LoginService {
    SaTokenInfo login(LoginPasswordTo userInfo);

    void register(LoginRegisterTo userInfo);
}
