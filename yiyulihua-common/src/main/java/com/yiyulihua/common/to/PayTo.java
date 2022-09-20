package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author sunbo
 * @since 2022/09/19 17:29
 */

@Data
@ApiModel(value = "PayTo", description = "调用支付页面")
public class PayTo {

    @ApiModelProperty("商品类型: 0: 作品 1: 广告")
    @NotNull
    private Integer type;

    @ApiModelProperty("订单号 orderNo")
    @NotNull
    @Min(1000000000000000L)
    @Max(Long.MAX_VALUE)
    private Long orderNo;
}
