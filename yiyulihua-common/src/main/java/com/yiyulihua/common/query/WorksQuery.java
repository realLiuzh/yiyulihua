package com.yiyulihua.common.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @since  2022-07-22-17:47
 */
@ApiModel(value = "WorksQuery", description = "作品查询条件对象")
@Data
public class WorksQuery {
    /**
     * 当前页
     */
    @ApiModelProperty(value = "分页查询当前页", required = true)
    private Integer currentPage;
    /**
     * 每页记录条数
     */
    @ApiModelProperty(value = "分页查询每页条数", required = true)
    private Integer pageSize;
    /**
     * 按时间远近排序
     */
    @ApiModelProperty("按时间远近升降排序")
    private Integer timeSort = 0;
    /**
     * 按价格排序
     */
    @ApiModelProperty("按价格升降排序")
    private Integer priceSort = 0;
    /**
     * 根据类型查询
     */
    @ApiModelProperty("根据作品类型查询")
    private Integer typeId;
    /**
     * 根据子类型查询
     */
    @ApiModelProperty("根据作品类型格式查询")
    private Integer subtypeId;
}
