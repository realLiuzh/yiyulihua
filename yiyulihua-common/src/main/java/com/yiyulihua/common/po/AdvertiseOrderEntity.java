package com.yiyulihua.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author snbo
 * @since 2022-08-24
 */
@TableName("tb_advertise_order")
@ApiModel(value = "AdvertiseOrder对象", description = "")
@Data
public class AdvertiseOrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单号")
    private Long orderNo;

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

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("逻辑删除(0:未删除 1:已删除)")
    @TableLogic
    private Integer isValid;

}
