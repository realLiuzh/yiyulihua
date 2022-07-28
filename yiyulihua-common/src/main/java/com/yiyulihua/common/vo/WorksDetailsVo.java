package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunbo
 * @date 2022-07-18-13:16
 */

@ApiModel(value = "WorksDetailsVo", description = "作品详情界面作品信息对象")
@Data
public class WorksDetailsVo {
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
     * 作品类型
     */
    @ApiModelProperty("作品类型(如: /图片/音频...)")
    private String type;
    /**
     * 作品子类型(格式)
     */
    @ApiModelProperty("作品子类型(如: jpg/png/mp3/flac/doc...)")
    private String subtype;
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
     * 作品图片
     */
    @ApiModelProperty("作品封面")
    private String worksCover;
    /**
     * 作品需求
     */
    @ApiModelProperty("作品需求")
    private String worksDemand;
    /**
     * 出价人数
     */
    @ApiModelProperty("出价人数")
    private Integer worksBidNumber;
    /**
     * 作品流程
     */
    @ApiModelProperty("作品流程")
    private String worksProcess;
    /**
     * 备注(文字备注为字数,图片备注为大小,音频备注为时长)
     */
    @ApiModelProperty("备注(文字作品备注为字数,图片作品备注为大小,音频作品备注为时长)")
    private String remark;

    @ApiModelProperty("作品截止时间")
    private Date worksDeadline;
}
