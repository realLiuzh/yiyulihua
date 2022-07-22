package com.yiyulihua.common.query;

import lombok.Data;

/**
 * @author sunbo
 * @date 2022-07-22-17:47
 */
@Data
public class WorksQuery {
    /**
     * 按时间远近排序
     */
    private Integer timeSort;
    /**
     * 按价格排序
     */
    private Integer priceSort;
    /**
     * 根据类型查询
     */
    private String typeId;
    /**
     * 根据子类型查询
     */
    private String subtypeId;

}
