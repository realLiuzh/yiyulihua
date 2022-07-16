package com.yiyulihua.task.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author snbo
 * @date 2022-07-16 16:53:42
 */
@Data
@TableName("tb_work")
public class WorkEntity implements Serializable {
    private static final long serialVersionUID = 212123456781L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 作品名称
     */
    private String workName;
    /**
     * 作品类型id
     */
    private String typeId;
    /**
     * 作品类型
     */
    private String type;
    /**
     * 作品路径
     */
    private String workPath;
    /**
     * 发布者id
     */
    private String publisherId;
    /**
     * 作品出价
     */
    private BigDecimal workPrice;
    /**
     * 作品截止日期
     */
    private Date workDeadline;
    /**
     * 作品图片
     */
    private String workCover;
    /**
     * 作品需求
     */
    private String workDemand;
    /**
     *
     */
    private String workProcess;
    /**
     * 出价人数
     */
    private Integer workBidNumber;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 是否有效(0:有效 1:无效)
     */
    private Integer isValid;
}
