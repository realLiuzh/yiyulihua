package com.yiyulihua.works.service.impl;

import com.yiyulihua.common.po.WorksEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;
import com.yiyulihua.common.utils.RRException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yiyulihua.works.dao.WorksDao;
import com.yiyulihua.works.service.WorksService;


@Service("workService")
public class WorksServiceImpl extends ServiceImpl<WorksDao, WorksEntity> implements WorksService {

    @Override
    public PageUtils queryPage(PageQuery params) {
        IPage<WorksEntity> page = this.page(
                new Query<WorksEntity>().getPage(params),
                new QueryWrapper<WorksEntity>()
        );
        return new PageUtils(page);
    }
}