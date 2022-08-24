package com.yiyulihua.works.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.po.AdvertiseOrderEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.to.AdvertiseOrderTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;
import com.yiyulihua.common.vo.AdvertiseOrderVo;
import com.yiyulihua.works.dao.AdvertiseOrderDao;
import com.yiyulihua.works.service.AdvertiseOrderService;
import com.yiyulihua.works.util.OrderNoGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-08-24
 */
@Service
public class AdvertiseOrderServiceImpl extends ServiceImpl<AdvertiseOrderDao, AdvertiseOrderEntity> implements AdvertiseOrderService {

    @Override
    public String createOrder(AdvertiseOrderTo advertiseOrderTo) {
        // 获取用户 id
        Object loginId = StpUtil.getLoginIdDefaultNull();
        AssertUtil.isTrue(null == loginId, new ApiException(ApiExceptionEnum.SIGNATURE_NOT_MATCH));
        String userId = loginId.toString();

        AdvertiseOrderEntity orderEntity = new AdvertiseOrderEntity();
        orderEntity.setOrderNo(OrderNoGenerator.uuid16());
        orderEntity.setWorksId(advertiseOrderTo.getWorksId());
        orderEntity.setUserId(userId);
        orderEntity.setPosition(advertiseOrderTo.getPosition());
        orderEntity.setNumber(advertiseOrderTo.getNumber());

        orderEntity.setPayStatus(0);

        int insert = baseMapper.insert(orderEntity);
        AssertUtil.isTrue(insert < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));

        return orderEntity.getOrderNo();
    }

    @Override
    public PageUtils<AdvertiseOrderVo> pageInfo(Integer current, Integer size) {

        Page<AdvertiseOrderEntity> page = new Query<AdvertiseOrderEntity>().getPage(new PageQuery(current, size));
        QueryWrapper<AdvertiseOrderEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "order_no", "works_id", "position", "number", "pay_status");

        baseMapper.selectPage(page, wrapper);
        List<AdvertiseOrderVo> list = new ArrayList<>();

        page.getRecords().forEach(entity -> {
            AdvertiseOrderVo advertiseOrderVo = new AdvertiseOrderVo();
            BeanUtils.copyProperties(entity, advertiseOrderVo);
            list.add(advertiseOrderVo);
        });

        return new PageUtils<>(list, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }
}
