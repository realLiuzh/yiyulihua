package com.yiyulihua.common.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"handler"})
@ApiModel("任务列表对象")
public class TaskListVo {
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
}
