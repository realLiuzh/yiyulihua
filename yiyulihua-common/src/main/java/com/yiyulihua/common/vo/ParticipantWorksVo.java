package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunbo
 * @date 2022/08/14 13:11
 */
@ApiModel(value = "ParticipantWorksVo", description = "我的参与界面作品vo")
@Data
public class ParticipantWorksVo {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("作品id")
    private Integer worksId;

    @ApiModelProperty("作品名称")
    private String worksName;

    @ApiModelProperty("作品类型")
    private String type;

    @ApiModelProperty("作品封面")
    private String worksCover;

    @ApiModelProperty("作品价格")
    private BigDecimal worksPrice;

    @ApiModelProperty("截止时间")
    private Date deadline;

    @ApiModelProperty("订单状态(0:已报价 1:被驳回 2:已完成 3:已购入)")
    private Integer orderStatus;

    @ApiModelProperty("支付状态(0:未支付 1:已支付)")
    private Integer payStatus;
}
