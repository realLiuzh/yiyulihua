package com.yiyulihua.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunbo
 * @date 2022-07-25-13:32
 */
@ApiModel(value = "Type", description = "类型对象")
@Data
public class TypeListVo {

    @ApiModelProperty("类型主键id")
    private Integer id;

    @ApiModelProperty("类型/格式")
    private String format;

    @ApiModelProperty(value = "子类型(格式)")
    private List<TypeListVo> children = new ArrayList<>();
}
