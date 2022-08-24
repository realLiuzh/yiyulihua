package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunbo
 * @date 2022-07-23-19:53
 */
@ApiModel(value = "WorksPublishVo", description = "作品发布或保存对象")
@Data
public class WorksPublishTo {

    /**
     * 作品名称
     */
    @ApiModelProperty(value = "作品名称", required = true)
    @NotBlank(message = "名称不能为空")
    @Length(max = 40, message = "名称不能超过40字")
    private String worksName;

    /**
     * 作品类型id
     */
    @ApiModelProperty(value = "作品类型 id", required = true)
    @NotBlank
    private String typeId;
    /**
     * 作品类型
     */
    @ApiModelProperty(value = "作品类型(如: 图片/音频...)", required = true)
    @NotBlank
    private String type;
    /**
     * 作品子类型id
     */
    @ApiModelProperty(value = "作品子类型(格式) id", required = true)
    @NotBlank
    private String subtypeId;
    /**
     * 作品子类型
     */
    @ApiModelProperty(value = "作品子类型(如: jpg/png/mp3/flac/doc...)", required = true)
    @NotBlank
    private String subtype;
    /**
     * 作品预览路径
     */
    @ApiModelProperty(value = "作品预览路径", required = true)
    @NotBlank
    private String previewUrl;
    /**
     * 作品真实路径
     */
    @ApiModelProperty(value = "作品真实路径", required = true)
    @NotBlank
    private String realUrl;
    /**
     * 作品封面
     */
    @ApiModelProperty("作品封面")
    private String worksCover;
    /**
     * 作品需求
     */
    @ApiModelProperty("作品需求")
    private String worksDemand;
    /**
     * 作品出价
     */
    @ApiModelProperty(value = "作品出价", required = true)
    @Digits(integer = 10, fraction = 2)
    @DecimalMin("00.00")
    private BigDecimal worksPrice;
    /**
     * 作品流程
     */
    @ApiModelProperty("作品流程")
    private String worksProcess;

    /**
     * 备注(文字备注为字数,图片备注为大小,音频备注为时长)
     */
    @ApiModelProperty(value = "备注(文字作品备注为字数,图片作品备注为大小,音频作品备注为时长)", required = true)
    @NotBlank
    private String remark;

    @ApiModelProperty(value = "作品状态(0:未发布 1:已发布(进行中))默认为 0", required = true)
    @NotNull
    @Min(0)
    @Max(1)
    private Integer worksStatus;

    @ApiModelProperty("作品截止时间")
    private Date worksDeadline;
}
