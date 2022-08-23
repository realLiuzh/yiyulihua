package com.yiyulihua.task.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.to.TaskBuildTo;
import com.yiyulihua.common.to.TaskCommitTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.utils.R;
import com.yiyulihua.common.vo.TaskListVo;
import com.yiyulihua.common.vo.TaskMyPublishVo;
import com.yiyulihua.common.vo.TaskVo;
import com.yiyulihua.common.vo.UserVo;
import com.yiyulihua.task.feign.UserFeignService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;

import com.yiyulihua.task.dao.TaskDao;
import com.yiyulihua.common.po.TaskEntity;
import com.yiyulihua.task.service.TaskService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskService {

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public PageUtils<TaskListVo> queryPage(PageQuery params) {
        QueryWrapper<TaskEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "task_name", "type", "task_price", "task_deadline", "task_picture");
        wrapper.orderByDesc("update_time");
        wrapper.eq("task_status", 1);
        wrapper.eq("is_valid", 0);


        Page<TaskEntity> ipage = new Query<TaskEntity>().getPage(params);
        Page<TaskEntity> page = this.baseMapper.selectPage(ipage, wrapper);


        List<TaskEntity> records = page.getRecords();
        List<TaskListVo> list = records.stream().map(taskEntity -> {
            TaskListVo taskListVo = new TaskListVo();
            BeanUtils.copyProperties(taskEntity, taskListVo);
            return taskListVo;
        }).collect(Collectors.toList());
        PageUtils<TaskListVo> pageUtils = new PageUtils<>(page);
        pageUtils.setList(list);
        return pageUtils;
    }

    @Override
    public TaskVo selectById(Integer id) {
        QueryWrapper<TaskEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "task_name", "type", "publisher_id", "task_price", "task_deadline", "task_picture", "task_demands", "task_works_number", "task_process");
        wrapper.eq("id", id);
        wrapper.eq("is_valid", 0);
        TaskEntity taskEntity = baseMapper.selectOne(wrapper);

        TaskVo taskVo = new TaskVo();
        BeanUtils.copyProperties(taskEntity, taskVo);

        R<UserVo> result = userFeignService.info(taskVo.getPublisherId());
//        UserVo userVo = result.getData();
        LinkedHashMap data = (LinkedHashMap) result.get("data");
//        UserVo userVo = (UserVo) data.get("data");
//        if (userVo != null) {
//            taskVo.setPublisherAvatar(userVo.getAvatar());
//            taskVo.setPublisherName(userVo.getUsername());
//        }
        if (data.get("avatar") != null)
            taskVo.setPublisherAvatar((String) data.get("avatar"));
        if (data.get("username") != null)
            taskVo.setPublisherName((String) data.get("username"));
        return taskVo;

    }

    @Override
    public PageUtils<TaskMyPublishVo> getByPublisherId(Integer current, Integer size) {
        // 根据 token 获取用户id
        Object loginId = StpUtil.getLoginIdDefaultNull();
        AssertUtil.isTrue(null == loginId, new ApiException(ApiExceptionEnum.SIGNATURE_NOT_MATCH));
        String publisherId = loginId.toString();

        //条件
        QueryWrapper<TaskEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "task_name", "type", "task_price", "task_deadline", "task_picture", "task_works_number");
        wrapper.eq("publisher_id", publisherId);
        wrapper.orderByAsc("create_time");
        wrapper.eq("is_valid", 0);

        //分页
        Page<TaskEntity> page = new Query<TaskEntity>().getPage(new PageQuery(current, size));
        baseMapper.selectPage(page, wrapper);

        List<TaskEntity> tasks = page.getRecords();
        List<TaskMyPublishVo> list = tasks.stream().map(taskEntity -> {
            TaskMyPublishVo taskListVo = new TaskMyPublishVo();
            BeanUtils.copyProperties(taskEntity, taskListVo);
            return taskListVo;
        }).collect(Collectors.toList());

        return new PageUtils<>(list, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Override
    public void buildTask(TaskBuildTo taskBuildTo) {
        TaskEntity taskEntity = new TaskEntity();
        BeanUtils.copyProperties(taskBuildTo, taskEntity);
        taskEntity.setTaskWorksNumber(0);
        int insert = this.baseMapper.insert(taskEntity);
        AssertUtil.isTrue(insert != 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }

    @Override
    public List<TaskListVo> recommendTask() {
        return baseMapper.recommendTask();
    }

    @Override
    public PageUtils<TaskListVo> getCollect(Integer current, Integer size) {
        // 根据 token 获取用户id
        Object loginId = StpUtil.getLoginIdDefaultNull();
        AssertUtil.isTrue(null == loginId, new ApiException(ApiExceptionEnum.SIGNATURE_NOT_MATCH));
        String userId = loginId.toString();

        //分页
        Page<TaskListVo> page = new Query<TaskListVo>().getPage(new PageQuery(current, size));
        List<Integer> ids = baseMapper.getCollect(page, userId);

        List<TaskListVo> records = ids.stream().map(this::selectTaskListVoById).collect(Collectors.toList());
        PageUtils<TaskListVo> pageUtils = new PageUtils<>(page);
        pageUtils.setList(records);
        return pageUtils;

    }

    @Override
    public void collectTask(Integer taskId, Integer status) {
        // 根据 token 获取用户id
        Object loginId = StpUtil.getLoginIdDefaultNull();
        AssertUtil.isTrue(null == loginId, new ApiException(ApiExceptionEnum.SIGNATURE_NOT_MATCH));
        String userId = loginId.toString();

        this.baseMapper.unCollect(userId, taskId);
        if (status == 1)
            this.baseMapper.collect(userId, taskId);
    }

    @Override
    public PageUtils<TaskListVo> getJoin(Integer current, Integer size) {
        // 根据 token 获取用户id
        Object loginId = StpUtil.getLoginIdDefaultNull();
        AssertUtil.isTrue(null == loginId, new ApiException(ApiExceptionEnum.SIGNATURE_NOT_MATCH));
        String userId = loginId.toString();

        //分页
        Page<TaskListVo> page = new Query<TaskListVo>().getPage(new PageQuery(current, size));
        List<Integer> ids = baseMapper.getJoin(page, userId);

        List<TaskListVo> records = ids.stream().map(this::selectTaskListVoById).collect(Collectors.toList());
        PageUtils<TaskListVo> pageUtils = new PageUtils<>(page);
        pageUtils.setList(records);
        return pageUtils;
    }


    public TaskListVo selectTaskListVoById(Integer id) {
        QueryWrapper<TaskEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "task_name", "type", "task_price", "task_deadline", "task_picture");
        wrapper.orderByDesc("update_time");
        wrapper.eq("task_status", 1);
        wrapper.eq("is_valid", 0);
        wrapper.eq("id", id);

        TaskListVo taskListVo = new TaskListVo();
        TaskEntity taskEntity = this.baseMapper.selectOne(wrapper);
        BeanUtils.copyProperties(taskEntity, taskListVo);
        return taskListVo;

    }

}