package com.yiyulihua.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.task.entity.WorkEntity;

import java.util.Map;

/**
 * 
 *
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-16 16:53:42
 */
public interface WorkService extends IService<WorkEntity> {

    PageUtils queryPage(PageQuery params);
}

