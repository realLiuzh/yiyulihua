package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author sunbo
 * @date 2022/08/14 18:30
 */

@ApiModel(value = "OrderTo", description = "创建订单对象")
@Data
public class OrderTo {

    @ApiModelProperty(value = "作品id",required = true)
    private Integer worksId;

    @ApiModelProperty(value = "买方报价",required = true)
    private BigDecimal quotePrice;

    @ApiModelProperty("订单名称")
    private String title;
}
