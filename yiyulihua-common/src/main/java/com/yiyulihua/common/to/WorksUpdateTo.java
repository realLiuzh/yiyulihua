package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunbo
 * @date 2022-07-24-19:32
 */
@ApiModel(value = "WorksUpdateVo", description = "作品更新对象")
@Data
public class WorksUpdateTo {
    /**
     * 作品主键 id
     */
    @ApiModelProperty(value = "作品主键 id(更新作品信息时必填)", required = true)
    private String id;
    /**
     * 作品名称
     */
    @ApiModelProperty(value = "作品名称", required = false)
    private String worksName;

    /**
     * 作品类型id
     */
    @ApiModelProperty(value = "作品类型 id", required = false)
    private String typeId;
    /**
     * 作品类型
     */
    @ApiModelProperty(value = "作品类型(如: text/image/audio...)", required = false)
    private String type;
    /**
     * 作品子类型id
     */
    @ApiModelProperty(value = "作品子类型(格式) id", required = false)
    private String subtypeId;
    /**
     * 作品子类型
     */
    @ApiModelProperty(value = "作品子类型(如: jpg/png/mp3/flac/doc...)", required = false)
    private String subtype;
    /**
     * 作品预览路径
     */
    @ApiModelProperty(value = "作品预览路径", required = false)
    private String previewUrl;
    /**
     * 作品真实路径
     */
    @ApiModelProperty(value = "作品真实路径", required = false)
    private String realUrl;
    /**
     * 作品封面
     */
    @ApiModelProperty(value = "作品封面", required = false)
    private String worksCover;
    /**
     * 作品需求
     */
    @ApiModelProperty(value = "作品需求", required = false)
    private String worksDemand;
    /**
     * 作品出价
     */
    @ApiModelProperty(value = "作品出价", required = false)
    private BigDecimal worksPrice;
    /**
     * 作品流程
     */
    @ApiModelProperty(value = "作品流程", required = false)
    private String worksProcess;

    /**
     * 备注(文字备注为字数,图片备注为大小,音频备注为时长)
     */
    @ApiModelProperty(value = "备注(文字作品备注为字数,图片作品备注为大小,音频作品备注为时长)", required = false)
    private String remark;

    @ApiModelProperty(value = "作品状态(0:未发布 1:已发布)默认为 0", required = false)
    private Integer worksStatus;

    @ApiModelProperty("作品截止时间")
    private Date worksDeadline;
}
