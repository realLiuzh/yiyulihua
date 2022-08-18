package com.yiyulihua.common.po;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_task_commit")
public class TaskCollectCommitEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 任务id
     */
    private Integer taskId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 作品url
     */
    private String url;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 是否有效
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isValid;
}
