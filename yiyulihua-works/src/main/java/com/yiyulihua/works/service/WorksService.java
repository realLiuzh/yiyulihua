package com.yiyulihua.works.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.po.WorksEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.utils.PageUtils;

/**
 * 
 *
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-16 16:53:42
 */
public interface WorksService extends IService<WorksEntity> {

    PageUtils queryPage(PageQuery params);
}

