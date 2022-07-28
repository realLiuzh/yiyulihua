package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TaskVo {
    /**
     * 主键
     */
    @ApiModelProperty("唯一标识")
    private Integer id;
    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String taskName;
    /**
     * 任务类型
     */
    @ApiModelProperty("任务类型")
    private String type;
    /**
     * 任务出价
     */
    @ApiModelProperty("任务出价")
    private BigDecimal taskPrice;
    /**
     * 任务截止日期
     */
    @ApiModelProperty("任务截止日期")
    private Date taskDeadline;
    /**
     * 任务图片
     */
    @ApiModelProperty("任务图片")
    private String taskPicture;
    /**
     * 发布者id
     */
    @ApiModelProperty("发布者id")
    private Integer publisherId;
    /**
     * 发布者头像
     */
    @ApiModelProperty("发布者头像")
    private String publisherAvatar;
    /**
     * 发布者名字
     */
    @ApiModelProperty("发布者名字")
    private String publisherName;
    /**
     * 任务需求
     */
    @ApiModelProperty("任务需求")
    private String taskDemands;
    /**
     * 任务提交作品数量
     */
    @ApiModelProperty("任务提交作品数量")
    private Integer taskWorksNumber;
    /**
     * 任务参与流程
     */
    @ApiModelProperty("任务参与流程")
    private String taskProcess;
}
