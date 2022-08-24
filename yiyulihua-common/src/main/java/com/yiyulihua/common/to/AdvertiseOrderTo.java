package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author sunbo
 * @since 2022/08/24 14:02
 */
@ApiModel(value = "AdvertiseOrderTo", description = "广告订单对象")
@Data
public class AdvertiseOrderTo {

    @ApiModelProperty(value = "作品id", required = true)
    @NotEmpty(message = "id 不能为空")
    @Length(min = 19, max = 19,message = "id 格式错误")
    private String worksId;

    @ApiModelProperty(value = "广告位置(0: 上方,1:下方)", required = true)
    @NotNull(message = "位置信息不能为空")
    @Min(value = 0,message = "位置信息格式错误")
    @Max(value = 1,message = "位置信息格式错误")
    private Integer position;

    @ApiModelProperty("订单数")
    @NotNull(message = "订单数不能为空")
    @Min(value = 1,message = "订单数小于 1")
    private Integer number;
}
