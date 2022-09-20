package com.yiyulihua.order.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
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
import com.yiyulihua.order.util.OrderConstants;
import com.yiyulihua.order.util.RedisIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    private final RedisIdWorker redisIdWorker;

    @Autowired
    public WorksOrderServiceImpl(WorksClient worksClient, RedisIdWorker redisIdWorker) {
        this.worksClient = worksClient;
        this.redisIdWorker = redisIdWorker;
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
    public Long createOrder(OrderTo order) {
        //获取用户id
        int userId = StpUtil.getLoginIdAsInt();

        WorksOrderEntity worksOrderEntity = new WorksOrderEntity();
        worksOrderEntity.setWorksId(order.getWorksId());
        worksOrderEntity.setQuotePrice(order.getQuotePrice());
        worksOrderEntity.setOrderNo(redisIdWorker.nextId("order:works:no"));
        worksOrderEntity.setParticipantId(userId);
        worksOrderEntity.setOrderTitle(order.getTitle());

        //默认已报价
        worksOrderEntity.setOrderStatus(OrderConstants.QUOTED.getCode());
        //默认未支付
        worksOrderEntity.setPayStatus(OrderConstants.UNPAID.getCode());

        int insert = baseMapper.insert(worksOrderEntity);
        AssertUtil.isTrue(insert < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));

        //更新作品的报价人数
        Result<?> result = worksClient.updateNumber(order.getWorksId());
        AssertUtil.isTrue(!result.getCode().equals(ApiExceptionEnum.SUCCESS.getResultCode()), new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));

        return worksOrderEntity.getOrderNo();
    }


    @Override
    public void updateOrderStatusByOrderNo(String orderNo, OrderConstants... codeMsg) {
        UpdateChainWrapper<WorksOrderEntity> wrapper = update();
        for (OrderConstants constants : codeMsg) {
            wrapper.set(constants.getMsg(), constants.getCode());
        }
        boolean update = wrapper
                .eq("order_no", orderNo)
                .update();

        AssertUtil.isTrue(!update, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }
}
