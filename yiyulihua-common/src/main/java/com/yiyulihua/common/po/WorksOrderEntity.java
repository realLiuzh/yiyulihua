package com.yiyulihua.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author snbo
 * @since 2022-08-14
 */
@Data
@TableName("tb_works_order")
@ApiModel(value = "WorksOrder对象", description = "作品订单对象")
public class WorksOrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("作品id")
    private String worksId;

    @ApiModelProperty("买方报价")
    private BigDecimal quotePrice;

    @ApiModelProperty("订单状态(0:已报价 1:被驳回 2:已完成 3:已购入)")
    private Integer orderStatus;

    @ApiModelProperty("支付状态(0:未支付 1:已支付)")
    private Integer payStatus;

    @ApiModelProperty("买方id")
    private String participantId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("逻辑删除")
    @TableLogic
    private Integer isValid;
}
