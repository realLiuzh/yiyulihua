/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.yiyulihua.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyulihua.common.query.PageQuery;

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Query<T> {

    public Page<T> getPage(PageQuery params) {
        return this.getPage(params, null, false);
    }

    public Page<T> getPage(PageQuery query, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 6;

        if (query.getPage() != null) {
            curPage = Long.parseLong("" + query.getPage());
        }
        if (query.getLimit() != null) {
            limit = Long.parseLong("" + query.getLimit());
        }

        //分页对象
        return new Page<>(curPage, limit);
    }
}
