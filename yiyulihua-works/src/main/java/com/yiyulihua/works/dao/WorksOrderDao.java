package com.yiyulihua.works.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiyulihua.common.po.WorksOrderEntity;
import com.yiyulihua.common.vo.ParticipantWorksVo;
import com.yiyulihua.common.vo.WorksListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author snbo
 * @since 2022-08-14
 */
@Mapper
public interface WorksOrderDao extends BaseMapper<WorksOrderEntity> {

    IPage<ParticipantWorksVo> getParticipantInfoByUserId(IPage<ParticipantWorksVo> page, @Param(Constants.WRAPPER) Wrapper<ParticipantWorksVo> wrapper);
}
