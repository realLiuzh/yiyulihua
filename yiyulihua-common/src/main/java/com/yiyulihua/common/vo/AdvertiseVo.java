package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author sunbo
 * @date 2022/08/13 13:25
 */
@Data
@ApiModel(value = "AdvertiseVo类", description = "广告信息类")
public class AdvertiseVo {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("广告标题")
    private String title;

    @ApiModelProperty("封面路径")
    private String coverUrl;

    @ApiModelProperty("链接地址")
    private String linkUrl;

    @ApiModelProperty("广告位置(0:首页广告 1:底部广告)")
    private Integer position;

    @ApiModelProperty("广告权重顺序,默认为0,权重越大越靠前")
    private Integer sort;

}
