package com.yiyulihua.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.to.TaskBuildTo;
import com.yiyulihua.common.to.TaskCommitTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.po.TaskEntity;
import com.yiyulihua.common.vo.TaskListVo;
import com.yiyulihua.common.vo.TaskMyPublishVo;
import com.yiyulihua.common.vo.TaskVo;

import java.util.List;

/**
 * @author lzh
 * @email 1138423425@qq.com
 * @date 2022-07-15 21:01:57
 */
public interface TaskService extends IService<TaskEntity> {

    PageUtils<TaskListVo> queryPage(PageQuery params);

    TaskVo selectById(Integer id);

    PageUtils<TaskMyPublishVo> getByPublisherId(Integer current, Integer size);

    void buildTask(TaskBuildTo taskBuildTo);

    List<TaskListVo> recommendTask();

    PageUtils<TaskListVo> getCollect(Integer current, Integer size);

    void collectTask(Integer taskId, Integer status);

    PageUtils<TaskListVo> getJoin(Integer current, Integer size);
}

