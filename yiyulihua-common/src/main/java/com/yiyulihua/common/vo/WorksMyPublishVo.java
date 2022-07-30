package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunbo
 * @date 2022-07-25-17:02
 */
@ApiModel(value = "WorksPublisherVo", description = "我的发布界面作品对象")
@Data
public class WorksMyPublishVo {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("作品名称")
    private String worksName;

    @ApiModelProperty("作品封面")
    private String worksCover;


    @ApiModelProperty("作品类型(如: 图片/音频...)")
    private String type;


    @ApiModelProperty("作品子类型(如: jpg/png/mp3/flac/doc...)")
    private String subtype;

    @ApiModelProperty("作品出价")
    private BigDecimal worksPrice;

    @ApiModelProperty("出价人数")
    private Integer worksBidNumber;

    @ApiModelProperty("作品截止时间")
    private Date worksDeadline;
}
