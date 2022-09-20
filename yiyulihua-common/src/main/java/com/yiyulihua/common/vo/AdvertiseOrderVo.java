package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @since 2022/08/24 15:09
 */
@Data
public class AdvertiseOrderVo {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("作品id")
    private Integer worksId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("广告位置(0: 上方,1:下方)")
    private Integer position;

    @ApiModelProperty("订单数")
    private Integer number;

    @ApiModelProperty("支付状态(0:未支付 1:已支付)")
    private Integer payStatus;
}
