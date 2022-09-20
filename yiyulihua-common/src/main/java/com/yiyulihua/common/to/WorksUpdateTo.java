package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunbo
 * @since  2022-07-24-19:32
 */
@ApiModel(value = "WorksUpdateVo", description = "作品更新对象")
@Data
public class WorksUpdateTo {

    @ApiModelProperty(value = "作品主键 id(更新作品信息时必填)", required = true)
    @NotNull(message = "id 不能为空")
    @Range(min = 0, max = Integer.MAX_VALUE, message = "id 格式错误")
    private Integer id;

    @ApiModelProperty(value = "作品名称")
    @Length(max = 40)
    private String worksName;


    @ApiModelProperty(value = "作品类型 id")
    private Integer typeId;

    @ApiModelProperty(value = "作品类型(如: text/image/audio...)")
    private String type;

    @ApiModelProperty(value = "作品子类型(格式) id")
    private Integer subtypeId;

    @ApiModelProperty(value = "作品子类型(如: jpg/png/mp3/flac/doc...)")
    private String subtype;

    @ApiModelProperty(value = "作品预览路径")
    private String previewUrl;

    @ApiModelProperty(value = "作品真实路径")
    private String realUrl;

    @ApiModelProperty(value = "作品封面")
    private String worksCover;

    @ApiModelProperty(value = "作品需求")
    private String worksDemand;

    @ApiModelProperty(value = "作品出价")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal worksPrice;

    @ApiModelProperty(value = "作品流程")
    private String worksProcess;


    @ApiModelProperty(value = "备注(文字作品备注为字数,图片作品备注为大小,音频作品备注为时长)")
    private String remark;

    @ApiModelProperty(value = "作品状态(0:未发布 1:已发布(进行中) 2:待确认 3:已售出)")
    private Integer worksStatus;

    @ApiModelProperty("作品截止时间")
    private Date worksDeadline;
}
