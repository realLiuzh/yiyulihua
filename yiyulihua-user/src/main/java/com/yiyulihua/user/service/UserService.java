package com.yiyulihua.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.po.UserEntity;
import com.yiyulihua.common.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-16 18:12:48
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserVo getById(Integer id);

    List<Integer> getAllUserId();
}

