package com.yiyulihua.order.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.po.WorksEntity;
import com.yiyulihua.common.po.WorksOrderEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.OrderTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;
import com.yiyulihua.common.vo.ParticipantWorksVo;
import com.yiyulihua.order.dao.WorksOrderDao;
import com.yiyulihua.order.feign.WorksClient;
import com.yiyulihua.order.service.WorksOrderService;
import com.yiyulihua.order.util.OrderNoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-08-14
 */
@Service
public class WorksOrderServiceImpl extends ServiceImpl<WorksOrderDao, WorksOrderEntity> implements WorksOrderService {

    private final WorksClient worksClient;

    @Autowired
    public WorksOrderServiceImpl(WorksClient worksClient) {
        this.worksClient = worksClient;
    }


    @Override
    public PageUtils<ParticipantWorksVo> getWorksOrderByUserId(Integer current, Integer size) {
        // 获取用户 id
        Object loginId = StpUtil.getLoginIdDefaultNull();
        AssertUtil.isTrue(null == loginId, new ApiException(ApiExceptionEnum.SIGNATURE_NOT_MATCH));
        String userId = loginId.toString();

        //条件
        QueryWrapper<ParticipantWorksVo> wrapper = new QueryWrapper<>();
        wrapper.eq("wo.participant_id", userId);
        wrapper.orderByDesc("wo.update_time");

        Page<ParticipantWorksVo> page = new Query<ParticipantWorksVo>().getPage(new PageQuery(current, size));
        baseMapper.getParticipantInfoByUserId(page, wrapper);

        return new PageUtils<>(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Override
    public String createOrder(OrderTo order) {
        AssertUtil.isTrue(StringUtils.isEmpty(order) || null == order.getQuotePrice(), new ApiException(ApiExceptionEnum.BODY_NOT_MATCH));
        //获取用户id
        Object loginId = StpUtil.getLoginIdDefaultNull();
        AssertUtil.isTrue(null == loginId, new ApiException(ApiExceptionEnum.BODY_NOT_MATCH));
        String userId = loginId.toString();

        WorksOrderEntity worksOrderEntity = new WorksOrderEntity();
        worksOrderEntity.setWorksId(order.getWorksId());
        worksOrderEntity.setQuotePrice(order.getQuotePrice());
        worksOrderEntity.setOrderNo(OrderNoGenerator.uuid16());
        worksOrderEntity.setParticipantId(userId);

        //默认已报价
        worksOrderEntity.setOrderStatus(0);
        //默认未支付
        worksOrderEntity.setPayStatus(0);

        int insert = baseMapper.insert(worksOrderEntity);
        AssertUtil.isTrue(insert < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));

        //更新作品的报价人数
        Result<?> result = worksClient.updateNumber(order.getWorksId());
        AssertUtil.isTrue(!result.getCode().equals("0"), new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));

        return worksOrderEntity.getOrderNo();
    }
}
