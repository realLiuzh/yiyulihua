package com.yiyulihua.task.service.impl;

import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yiyulihua.task.dao.WorkDao;
import com.yiyulihua.task.entity.WorkEntity;
import com.yiyulihua.task.service.WorkService;


@Service("workService")
public class WorkServiceImpl extends ServiceImpl<WorkDao, WorkEntity> implements WorkService {

    @Override
    public PageUtils queryPage(PageQuery params) {
        IPage<WorkEntity> page = this.page(
                new Query<WorkEntity>().getPage(params),
                new QueryWrapper<WorkEntity>()
        );

        return new PageUtils(page);
    }
}