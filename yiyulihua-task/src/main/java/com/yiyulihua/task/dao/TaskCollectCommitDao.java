package com.yiyulihua.task.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyulihua.common.po.TaskCollectCommitEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskCollectCommitDao extends BaseMapper<TaskCollectCommitEntity> {
    Integer isCommitted(@Param("taskId") Integer taskId, @Param("userId") Integer userId);
}
