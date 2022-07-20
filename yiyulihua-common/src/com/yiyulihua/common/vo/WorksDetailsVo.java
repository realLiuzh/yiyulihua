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
     * 作品路径
     */
    private String worksPath;
    /**
     * 发布者id
     */
    private String publisherId;
    /**
     * 发布者昵称
     */
    private String publisherNickname;
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
}
