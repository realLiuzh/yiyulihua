package com.yiyulihua.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.yiyulihua.common.to.LoginCodeTo;
import com.yiyulihua.common.to.LoginPasswordTo;
import com.yiyulihua.common.to.LoginRegisterTo;
import com.yiyulihua.common.vo.UserLoginVo;

public interface LoginService {
    UserLoginVo loginByPassword(LoginPasswordTo userInfo);

    UserLoginVo register(LoginRegisterTo userInfo);

    UserLoginVo loginByCode(LoginCodeTo userInfo);
}
