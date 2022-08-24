package com.yiyulihua.works.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.po.AdvertiseOrderEntity;
import com.yiyulihua.common.to.AdvertiseOrderTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.AdvertiseOrderVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author snbo
 * @since 2022-08-24
 */
public interface AdvertiseOrderService extends IService<AdvertiseOrderEntity> {

    String createOrder(AdvertiseOrderTo advertiseOrderTo);

    PageUtils<AdvertiseOrderVo> pageInfo(Integer current, Integer size);
}
