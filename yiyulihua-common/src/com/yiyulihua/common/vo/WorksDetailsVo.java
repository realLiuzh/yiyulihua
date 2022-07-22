package com.yiyulihua.common.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunbo
 * @date 2022-07-18-13:16
 */

@Data
public class WorksDetailsVo {
    /**
     * 主键
     */
    private String id;
    /**
     * 作品名称
     */
    private String worksName;
    /**
     * 发布者id
     */
    private String publisherId;
    /**
     * 发布者昵称
     */
    private String publisherNickname;
    /**
     * 作品真实路径
     */
    private String previewUrl;
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
     * 出价人数
     */
    private Integer worksBidNumber;
    /**
     * 作品流程
     */
    private String worksProcess;
    /**
     * 备注(文字备注为字数,图片备注为大小,音频备注为时长)
     */
    private String remark;
}
