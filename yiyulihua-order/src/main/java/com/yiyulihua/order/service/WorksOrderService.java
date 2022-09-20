package com.yiyulihua.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.po.WorksOrderEntity;
import com.yiyulihua.common.to.OrderTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.ParticipantWorksVo;
import com.yiyulihua.order.util.OrderConstants;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author snbo
 * @since 2022-08-14
 */
public interface WorksOrderService extends IService<WorksOrderEntity> {

    PageUtils<ParticipantWorksVo> getWorksOrderByUserId(Integer current, Integer size);

    Long createOrder(OrderTo order);

    void updateOrderStatusByOrderNo(String orderNo, OrderConstants... codeMsg);
}
