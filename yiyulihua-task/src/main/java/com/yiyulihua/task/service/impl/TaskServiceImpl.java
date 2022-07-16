package com.yiyulihua.task.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.vo.TaskListVo;
import com.yiyulihua.common.vo.TaskVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;

import com.yiyulihua.task.dao.TaskDao;
import com.yiyulihua.common.po.TaskEntity;
import com.yiyulihua.task.service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskService {

    @Override
    public PageUtils queryPage(PageQuery params) {
        QueryWrapper<TaskEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "task_name", "type", "task_price", "task_deadline", "task_picture");
        wrapper.orderByDesc("update_time");
        wrapper.eq("is_valid", 1);


        Page<TaskEntity> ipage = new Query<TaskEntity>().getPage(params);
        Page<TaskEntity> page = this.baseMapper.selectPage(ipage, wrapper);


        List<TaskEntity> records = page.getRecords();
        List<TaskListVo> list = records.stream().map(taskEntity -> {
            TaskListVo taskListVo = new TaskListVo();
            BeanUtils.copyProperties(taskEntity, taskListVo);
            return taskListVo;
        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(list);
        return pageUtils;
    }

    @Override
    public TaskVo selectByid(Integer id) {
        QueryWrapper<TaskEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "task_name", "type", "task_price", "task_deadline", "task_picture", "task_demands", "task_works_number", "task_process");
        wrapper.eq("id", id);
        wrapper.eq("is_valid", 1);
        TaskEntity taskEntity = baseMapper.selectOne(wrapper);

        TaskVo taskVo = new TaskVo();
        BeanUtils.copyProperties(taskEntity, taskVo);

        return taskVo;

    }

}