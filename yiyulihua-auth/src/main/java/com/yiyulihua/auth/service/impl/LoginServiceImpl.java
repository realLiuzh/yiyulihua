package com.yiyulihua.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.yiyulihua.auth.dao.LoginDao;
import com.yiyulihua.auth.service.LoginService;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.to.ApiToken;
import com.yiyulihua.common.to.LoginPasswordTo;
import com.yiyulihua.common.to.LoginRegisterTo;
import com.yiyulihua.common.to.UserLoginTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.vo.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户管理业务类
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private LoginDao loginDao;

    @Override
    public UserLoginVo login(LoginPasswordTo userInfo) {
        SaTokenInfo saTokenInfo = null;
        UserLoginTo userLoginTo = loadUserByEmail(userInfo.getEmail());
        AssertUtil.isTrue((userLoginTo == null || (!SaSecureUtil.md5(userInfo.getPassword()).equals(userLoginTo.getPassword()))),
                new ApiException(ApiExceptionEnum.LOGIN_ERROR));
        // 密码校验成功后登录，一行代码实现登录
        StpUtil.login(userLoginTo.getId());
        // 将用户信息存储到Session中
        StpUtil.getSession().set("userInfo", userLoginTo);
        // 获取当前登录用户Token信息
        saTokenInfo = StpUtil.getTokenInfo();

        UserLoginVo userLoginVo = new UserLoginVo();
        BeanUtils.copyProperties(userLoginTo, userLoginVo);
        userLoginVo.setToken(new ApiToken(saTokenInfo.getTokenName(), saTokenInfo.getTokenValue()));
        return userLoginVo;
    }

    @Override
    public UserLoginVo register(LoginRegisterTo userInfo) {
        String phone = userInfo.getPhone();
        AssertUtil.isTrue(loginDao.emailIsUsed(userInfo.getEmail()) != 0, new ApiException((ApiExceptionEnum.EMAIL_USED)));
        AssertUtil.isTrue(loginDao.phoneIsUsed(phone) != 0, new ApiException(ApiExceptionEnum.PHONE_USED));
        String code = redisTemplate.opsForValue().get(phone);
        AssertUtil.isTrue(code == null || !code.equals(userInfo.getCode()), new ApiException(ApiExceptionEnum.CODE_ERROR));
        userInfo.setIsValid(0);
        userInfo.setPassword(SaSecureUtil.md5(userInfo.getPassword()));
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        userInfo.setUsername(phone);
        Integer update = loginDao.register(userInfo);
        AssertUtil.isTrue(update == null, new ApiException(ApiExceptionEnum.REGISTER_ERROR));
        // 登录
        StpUtil.login(userInfo.getId());

        UserLoginTo userLoginTo = loadUserById(userInfo.getId());
        // 将用户信息存储到session中
        StpUtil.getSession().set("userInfo", userLoginTo);

        SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();

        UserLoginVo userLoginVo = new UserLoginVo();
        BeanUtils.copyProperties(userLoginTo, userLoginVo);
        userLoginVo.setToken(new ApiToken(saTokenInfo.getTokenName(), saTokenInfo.getTokenValue()));
        return userLoginVo;
    }

    private UserLoginTo loadUserById(Integer id) {
        return loginDao.loadUserById(id);
    }

    private UserLoginTo loadUserByEmail(String email) {
        return loginDao.loadUserByEmail(email);
    }
}