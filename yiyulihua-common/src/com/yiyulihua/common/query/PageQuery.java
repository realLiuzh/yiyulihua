package com.yiyulihua.common.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 页面查询
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQuery {
    // 页数
    private Integer page;
    // 每页条数
    private Integer limit;
}
