package com.yiyulihua.task.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyulihua.common.po.TaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyulihua.common.vo.TaskListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lzh
 * @email 1138423425@qq.com
 * @date 2022-07-15 21:01:57
 */
@Mapper
public interface TaskDao extends BaseMapper<TaskEntity> {
    List<TaskListVo> recommendTask();

    List<Integer> getCollect(IPage<?> page, @Param("userId") String userId);

    void unCollect(@Param("userId") String userId, @Param("taskId") Integer taskId);

    void collect(@Param("userId") String userId, @Param("taskId") Integer taskId);

    List<Integer> getJoin(Page<TaskListVo> page, @Param("userId") String userId);
}
