package com.yiyulihua.common.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author snbo
 * @date 2022-07-16 16:53:42
 */
@Data
@TableName("tb_work")
public class WorksEntity implements Serializable {
    private static final long serialVersionUID = 212123456781L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 作品名称
     */
    private String worksName;
    /**
     * 作品类型id
     */
    private String typeId;
    /**
     * 作品类型
     */
    private String type;

    /**
     * 作品子类型id
     */
    private String subtypeId;
    /**
     * 作品子类型
     */
    private String subtype;
    /**
     * 作品路径
     */
    private String worksPath;
    /**
     * 发布者id
     */
    private String publisherId;
    /**
     * 作品出价
     */
    private BigDecimal worksPrice;
    /**
     * 作品截止日期
     */
    private Date worksDeadline;
    /**
     * 作品图片
     */
    private String worksCover;
    /**
     * 作品需求
     */
    private String worksDemand;
    /**
     * 作品流程
     */
    private String worksProcess;
    /**
     * 出价人数
     */
    private Integer worksBidNumber;
    /**
     * 备注(文字备注为字数,图片备注为大小,音频备注为时长)
     */
    private String remark;
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
