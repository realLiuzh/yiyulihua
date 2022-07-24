package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author sunbo
 * @date 2022-07-18-15:46
 */
@ApiModel(value = "WorksListVo", description = "作品商店界面作品信息对象")
@Data
public class WorksListVo {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;
    /**
     * 作品名称
     */
    @ApiModelProperty("作品名称")
    private String worksName;
    /**
     * 作品封面
     */
    @ApiModelProperty("作品封面")
    private String worksCover;
    /**
     * 作品类型id
     */
    @ApiModelProperty("作品类型 id")
    private String typeId;
    /**
     * 作品类型
     */
    @ApiModelProperty("作品类型(如: text/image/audio...)")
    private String type;
    /**
     * 作品子类型id
     */
    @ApiModelProperty("作品子类型(格式) id")
    private String subtypeId;
    /**
     * 作品子类型
     */
    @ApiModelProperty("作品子类型(如: jpg/png/mp3/flac/doc...)")
    private String subtype;
    /**
     * 发布者id
     */
    @ApiModelProperty("发布者id")
    private String publisherId;
    /**
     * 发布者昵称
     */
    @ApiModelProperty("发布者昵称")
    private String publisherNickname;
    /**
     * 作品预览路径
     */
    @ApiModelProperty("作品预览路径")
    private String previewUrl;
    /**
     * 作品出价
     */
    @ApiModelProperty("作品出价")
    private BigDecimal worksPrice;
    /**
     * 出价人数
     */
    @ApiModelProperty("出价人数")
    private Integer worksBidNumber;
}
