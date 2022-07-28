package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunbo
 * @date 2022-07-25-19:17
 */
@ApiModel(value = "TaskMyPublishVo", description = "我的发布界面任务对象")
@Data
public class TaskMyPublishVo {
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
     * 任务提交作品数量
     */
    @ApiModelProperty("任务提交作品数量")
    private Integer taskWorksNumber;

}
