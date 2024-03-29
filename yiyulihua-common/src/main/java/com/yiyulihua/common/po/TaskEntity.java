package com.yiyulihua.common.po;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lzh
 * @email 1138423425@qq.com
 * @date 2022-07-15 21:01:57
 */
@Data
@TableName("tb_task")
public class TaskEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务类型id
     */
    private Integer typeId;
    /**
     * 任务类型
     */
    private String type;
    /**
     * 发布者id
     */
    private Integer publisherId;
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
    /**
     * 任务需求
     */
    private String taskDemands;
    /**
     * 任务提交作品数量
     */
    private Integer taskWorksNumber;
    /**
     * 任务参与流程
     */
    private String taskProcess;
    /**
     * 任务保存/发布
     */
    private Integer taskStatus;
    /**
     * 首页广告
     */
    private Integer frontPageAds;

    /**
     * 底部广告
     */
    private Integer bottomAds;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 是否有效
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isValid;
}
