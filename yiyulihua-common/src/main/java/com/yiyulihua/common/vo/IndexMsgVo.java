package com.yiyulihua.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author sunbo
 * @since 2022/10/05 21:07
 */

@ApiModel(value = "ResultMessageVo", description = "聊天界面首页信息类")
@Data
@EqualsAndHashCode(callSuper = true)
public class IndexMsgVo extends ResultMessageVo {

    @ApiModelProperty("用户头像")
    private String avatar;
}
