package com.yiyulihua.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserVo implements Serializable {
    private static final long serialVersionUID = 7L;
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
}
