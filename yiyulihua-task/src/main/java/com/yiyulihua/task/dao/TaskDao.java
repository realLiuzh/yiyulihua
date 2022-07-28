package com.yiyulihua.task.dao;

import com.yiyulihua.common.po.TaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyulihua.common.vo.TaskListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lzh
 * @email 1138423425@qq.com
 * @date 2022-07-15 21:01:57
 */
@Mapper
public interface TaskDao extends BaseMapper<TaskEntity> {
    List<TaskListVo> recommendTask();
}
