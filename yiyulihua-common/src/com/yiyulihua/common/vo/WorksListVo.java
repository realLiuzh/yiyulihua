package com.yiyulihua.common.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunbo
 * @date 2022-07-18-15:46
 */
@Data
public class WorksListVo {
    /**
     * 主键
     */
    private String id;
    /**
     * 作品名称
     */
    private String worksName;
    /**
     * 作品封面
     */
    private String worksCover;
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
     * 作品出价
     */
    private BigDecimal worksPrice;
    /**
     * 作品截止日期
     */
    private Date worksDeadline;
    /**
     * 出价人数
     */
    private Integer worksBidNumber;
}
