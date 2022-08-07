package com.yiyulihua.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.yiyulihua.common.to.LoginPasswordTo;
import com.yiyulihua.common.to.LoginRegisterTo;
import com.yiyulihua.common.vo.UserLoginVo;

public interface LoginService {
    UserLoginVo login(LoginPasswordTo userInfo);

    UserLoginVo register(LoginRegisterTo userInfo);
}
