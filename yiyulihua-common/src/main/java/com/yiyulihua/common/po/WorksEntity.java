package com.yiyulihua.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author snbo
 * @date 2022-07-16 16:53:42
 */
@ApiModel(value = "Works",description = "作品实体类")
@Data
@TableName("tb_works")
public class WorksEntity implements Serializable {
    private static final long serialVersionUID = 212123456781L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 作品名称
     */
    @ApiModelProperty("作品名称")
    private String worksName;
    /**
     * 作品类型id
     */
    @ApiModelProperty("作品类型 id")
    private String typeId;
    /**
     * 作品类型
     */
    @ApiModelProperty("作品类型(如: text/image/audio...)")
    private String type;
    /**
     * 作品子类型id
     */
    @ApiModelProperty("作品子类型(格式) id")
    private String subtypeId;
    /**
     * 作品子类型
     */
    @ApiModelProperty("作品子类型(如: jpg/png/mp3/flac/doc...)")
    private String subtype;
    /**
     * 作品预览路径
     */
    @ApiModelProperty("作品预览路径")
    private String previewUrl;
    /**
     * 作品真实路径
     */
    @ApiModelProperty("作品真实路径")
    private String realUrl;
    /**
     * 发布者id
     */
    @ApiModelProperty("发布者id")
    private String publisherId;
    /**
     * 作品出价
     */
    @ApiModelProperty("作品出价")
    private BigDecimal worksPrice;
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
     * 作品流程
     */
    @ApiModelProperty("作品流程")
    private String worksProcess;
    /**
     * 出价人数
     */
    @ApiModelProperty("出价人数")
    private Integer worksBidNumber;
    /**
     * 备注(文字备注为字数,图片备注为大小,音频备注为时长)
     */
    @ApiModelProperty("备注(文字作品备注为字数,图片作品备注为大小,音频作品备注为时长)")
    private String remark;

    @ApiModelProperty("作品状态(0:未发布 1:已发布(进行中) 2:待确认 3:已售出)")
    private Integer worksStatus;

    @ApiModelProperty("作品截止时间")
    private Date worksDeadline;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 是否有效(0:有效 1:无效)
     */
    @ApiModelProperty("是否有效(0:有效 1:无效)")
    @TableLogic
    private Integer isValid;
}
