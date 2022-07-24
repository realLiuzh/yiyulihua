package com.yiyulihua.works.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiyulihua.common.po.WorksEntity;
import com.yiyulihua.common.vo.WorksDetailsVo;
import com.yiyulihua.common.vo.WorksListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @date 2022-07-16 16:53:42
 */
@Mapper
public interface WorksDao extends BaseMapper<WorksEntity> {
    IPage<WorksListVo> getWorksList(IPage<WorksListVo> page, @Param(Constants.WRAPPER) Wrapper<WorksListVo> wrapper);

    WorksDetailsVo getWorksDetails(@Param(Constants.WRAPPER) Wrapper<WorksDetailsVo> wrapper);
}
