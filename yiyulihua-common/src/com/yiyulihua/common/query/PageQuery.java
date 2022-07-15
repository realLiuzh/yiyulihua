package com.yiyulihua.common.query;

import lombok.Data;

// 页面查询
@Data
public class PageQuery {
    // 页数
    private Integer page;
    // 每页条数
    private Integer limit;
}
