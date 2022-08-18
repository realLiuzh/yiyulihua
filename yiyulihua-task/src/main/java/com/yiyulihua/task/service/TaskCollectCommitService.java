package com.yiyulihua.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.po.TaskCollectCommitEntity;
import com.yiyulihua.common.to.TaskCommitTo;

public interface TaskCollectCommitService extends IService<TaskCollectCommitEntity> {
    void commit(TaskCommitTo taskCommitTo);
}
