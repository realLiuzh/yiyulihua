package com.yiyulihua.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.po.TaskCollectCommitEntity;
import com.yiyulihua.common.po.TaskEntity;
import com.yiyulihua.common.to.TaskCommitTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.task.dao.TaskCollectCommitDao;
import com.yiyulihua.task.dao.TaskDao;
import com.yiyulihua.task.service.TaskCollectCommitService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service("taskCollectCommitService")
public class TaskCollectCommitServiceImpl extends ServiceImpl<TaskCollectCommitDao, TaskCollectCommitEntity> implements TaskCollectCommitService {
    @Override
    public void commit(TaskCommitTo taskCommitTo) {
        AssertUtil.isTrue(isCommitted(taskCommitTo.getTaskId(), taskCommitTo.getUserId()), new ApiException(ApiExceptionEnum.IS_COMMITTED));
        TaskCollectCommitEntity taskEntity = new TaskCollectCommitEntity();
        BeanUtils.copyProperties(taskCommitTo, taskEntity);
        int insert = baseMapper.insert(taskEntity);
        AssertUtil.isTrue(insert != 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }

    private boolean isCommitted(Integer taskId, Integer userId) {
        return baseMapper.isCommitted(taskId, userId) > 0;
    }
}
