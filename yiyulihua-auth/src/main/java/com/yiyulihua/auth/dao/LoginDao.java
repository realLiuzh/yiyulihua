package com.yiyulihua.auth.dao;

import com.yiyulihua.common.to.UserLoginTo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginDao {
    UserLoginTo loadUserByUsername(@Param("username") String username);
}
