package com.yiyulihua.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * <p>
 *
 * </p>
 *
 * @author snbo
 * @since 2022-07-25
 */
@ApiModel(value = "Type对象", description = "")
@TableName("tb_type")
@Data
public class TypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("父格式 id( 父格式id为 0 为顶层格式(image/text))")
    private String parentId;

    @ApiModelProperty("格式")
    private String format;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("是否有效(0:有效 1:无效)")
    @TableLogic
    private Integer isValid;

}
