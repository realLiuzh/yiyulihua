package com.yiyulihua.common.vo;

import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class UserVo {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 企业/学校
     */
    private String organization;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否有效
     */
    private Integer isValid;

}
