package com.yiyulihua.task.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-15 21:01:57
 */
@Data
@TableName("tb_task")
public class TaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 发布者id
	 */
	private Integer publisherId;
	/**
	 * 发布者名称
	 */
	private String publishName;
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
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否有效
	 */
	private Integer isValid;

}
