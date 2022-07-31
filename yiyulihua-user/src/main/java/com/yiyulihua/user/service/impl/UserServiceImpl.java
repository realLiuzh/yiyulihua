package com.yiyulihua.user.service.impl;

import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;
import com.yiyulihua.common.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yiyulihua.user.dao.UserDao;
import com.yiyulihua.common.po.UserEntity;
import com.yiyulihua.user.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(null),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserVo getById(Integer id) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username", "avatar", "organization");
        wrapper.eq("id", id);
        wrapper.eq("is_valid", 1);
        UserEntity userEntity = baseMapper.selectOne(wrapper);
        if (userEntity == null) return null;
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userEntity, userVo);
        return userVo;
    }

    @Override
    public List<Integer> getAllUserId() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("is_valid", 1);
        List<UserEntity> userEntities = baseMapper.selectList(wrapper);

        List<Integer> list = new ArrayList<>();
        userEntities.forEach(user -> {
            list.add(user.getId());
        });

        return list;
    }

}