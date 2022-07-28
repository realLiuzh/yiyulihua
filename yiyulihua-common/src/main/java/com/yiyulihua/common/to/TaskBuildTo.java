package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("任务发布对象")
public class TaskBuildTo {

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称", required = true)
    private String taskName;
    /**
     * 任务类型id
     */
    @ApiModelProperty(value = "任务类型id", required = true)
    private Integer typeId;
    /**
     * 任务类型
     */
    @ApiModelProperty(value = "任务类型", required = true)
    private String type;
    /**
     * 发布者id
     */
    @ApiModelProperty(value = "发布者id", required = true)
    private Integer publisherId;
    /**
     * 任务出价
     */
    @ApiModelProperty(value = "任务出价", required = true)
    private BigDecimal taskPrice;
    /**
     * 任务图片
     */
    @ApiModelProperty(value = "任务封面图片url", required = true)
    private String taskPicture;
    /**
     * 任务需求
     */
    @ApiModelProperty(value = "任务需求", required = true)
    private String taskDemands;
    /**
     * 任务保存/发布
     */
    @ApiModelProperty(value = "任务保存/发布(0代表保存,1代表发布)", required = true)
    private Integer taskStatus;
    /**
     * 任务截止日期
     */
    @ApiModelProperty(value = "任务截止日期", required = true)
    private Date taskDeadline;

    /**
     * 首页广告
     */
    @ApiModelProperty(value = "首页广告天数(0、15...)", required = true)
    private Integer frontPageAds;

    /**
     * 底部广告
     */
    @ApiModelProperty(value = "底部广告天数(0、15...)", required = true)
    private Integer bottomAds;
}
