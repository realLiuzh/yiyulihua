package com.yiyulihua.task.service.impl;

import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.query.TaskQuery;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;

import com.yiyulihua.task.dao.TaskDao;
import com.yiyulihua.task.entity.TaskEntity;
import com.yiyulihua.task.service.TaskService;


@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskService {

    @Override
    public PageUtils queryPage(PageQuery params) {
        QueryWrapper<TaskEntity> wrapper = new QueryWrapper<>();

        wrapper.select("id", "task_name", "task_price", "task_deadline", "task_picture", "task_demands");
        wrapper.orderByDesc("update_time");
        IPage<TaskEntity> page = this.page(
                new Query<TaskEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}