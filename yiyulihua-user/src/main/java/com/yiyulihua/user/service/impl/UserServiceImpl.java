package com.yiyulihua.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.com.yiyulihua.common.utils.Query;

import com.yiyulihua.user.dao.UserDao;
import com.yiyulihua.user.entity.UserEntity;
import com.yiyulihua.user.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

}