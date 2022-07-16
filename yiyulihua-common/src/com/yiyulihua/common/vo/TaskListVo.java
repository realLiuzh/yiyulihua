package com.yiyulihua.common.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class TaskListVo {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务类型
     */
    private String type;
    /**
     * 任务出价
     */
    private BigDecimal taskPrice;
    /**
     * 任务截止日期
     */
    private Date taskDeadline;
    /**
     * 任务图片
     */
    private String taskPicture;
}
