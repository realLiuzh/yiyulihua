package com.yiyulihua.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @since  2022/08/13 14:30
 */
@Data
@ApiModel(value = "AdvertiseTo", description = "添加/修改广告对象")
public class AdvertiseTo {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty(value = "广告标题", required = true)
    private String title;

    @ApiModelProperty(value = "封面路径", required = true)
    private String coverUrl;

    @ApiModelProperty(value = "链接地址", required = true)
    private String linkUrl;

    @ApiModelProperty(value = "广告位置(0:首页广告 1:底部广告)", required = true)
    private Integer position;

    @ApiModelProperty(value = "广告权重顺序,默认为0,权重越大越靠前")
    private Integer sort;

}
