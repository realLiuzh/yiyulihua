package com.yiyulihua.order.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
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
import com.yiyulihua.order.dao.AdvertiseOrderDao;
import com.yiyulihua.order.service.AdvertiseOrderService;
import com.yiyulihua.order.util.RedisIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    private final RedisIdWorker redisIdWorker;

    public AdvertiseOrderServiceImpl(RedisIdWorker redisIdWorker) {
        this.redisIdWorker = redisIdWorker;
    }


    @Override
    public String createOrder(AdvertiseOrderTo advertiseOrderTo) {
        // 获取用户 id
        int userId = StpUtil.getLoginIdAsInt();

        AdvertiseOrderEntity orderEntity = new AdvertiseOrderEntity();
        orderEntity.setOrderNo(redisIdWorker.nextId("order:adv"));
        orderEntity.setWorksId(advertiseOrderTo.getWorksId());
        orderEntity.setUserId(userId);
        orderEntity.setPosition(advertiseOrderTo.getPosition());
        orderEntity.setNumber(advertiseOrderTo.getNumber());

        orderEntity.setPayStatus(0);

        int insert = baseMapper.insert(orderEntity);
        AssertUtil.isTrue(insert < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));

        return orderEntity.getOrderNo().toString();
    }

    @Override
    public PageUtils<AdvertiseOrderVo> pageInfo(Integer current, Integer size) {

        Page<AdvertiseOrderEntity> page = new Query<AdvertiseOrderEntity>().getPage(new PageQuery(current, size));
        QueryWrapper<AdvertiseOrderEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "order_no", "works_id", "position", "number", "pay_status");

        baseMapper.selectPage(page, wrapper);


        List<AdvertiseOrderVo> list = page.getRecords()
                .stream()
                .map(ad -> BeanUtil.copyProperties(ad, AdvertiseOrderVo.class))
                .collect(Collectors.toList());

        return new PageUtils<>(list, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }
}
