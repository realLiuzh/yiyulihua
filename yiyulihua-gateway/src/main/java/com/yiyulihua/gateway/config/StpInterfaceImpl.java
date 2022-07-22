package com.yiyulihua.gateway.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.yiyulihua.common.to.UserLoginTo;

import java.util.List;

//@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的权限码列表
        UserLoginTo userLoginTo = (UserLoginTo) StpUtil.getSession().get("userInfo");
        return userLoginTo.getPermissionList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的角色码列表
        return null;
    }

}
