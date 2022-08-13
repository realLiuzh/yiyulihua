package com.yiyulihua.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author snbo
 * @since 2022-08-13
 */
@Data
@TableName("tb_advertise")
@ApiModel(value = "Advertise对象", description = "广告类实体")
public class AdvertiseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("广告标题")
    private String title;

    @ApiModelProperty("封面路径")
    private String coverUrl;

    @ApiModelProperty("链接地址")
    private String linkUrl;

    @ApiModelProperty("广告位置(0:首页广告 1:底部广告)")
    private Integer position;

    @ApiModelProperty("广告权重顺序")
    private Integer sort;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("逻辑删除(0: false,1:true)")
    @TableLogic
    private Integer isValid;
}
