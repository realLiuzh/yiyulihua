package com.yiyulihua.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.query.TaskQuery;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.task.entity.TaskEntity;

import java.util.Map;

/**
 * @author lzh
 * @email 1138423425@qq.com
 * @date 2022-07-15 21:01:57
 */
public interface TaskService extends IService<TaskEntity> {

    PageUtils queryPage(PageQuery params);
}

