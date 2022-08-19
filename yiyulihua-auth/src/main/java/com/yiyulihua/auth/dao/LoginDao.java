package com.yiyulihua.auth.dao;

import com.yiyulihua.common.to.LoginRegisterTo;
import com.yiyulihua.common.to.UserLoginTo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginDao {
    UserLoginTo loadUserByEmail(@Param("email") String email);

    int phoneIsUsed(@Param("phone") String phone);

    Integer register(@Param("user") LoginRegisterTo user);

    UserLoginTo loadUserById(@Param("id") Integer id);

    Integer emailIsUsed(@Param("email") String email);

    UserLoginTo loadUserByPhone(String phone);
}
