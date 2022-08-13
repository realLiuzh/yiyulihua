package com.yiyulihua.works.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.po.AdvertiseEntity;
import com.yiyulihua.common.to.AdvertiseTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.AdvertiseVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author snbo
 * @since 2022-08-13
 */
public interface AdvertiseService extends IService<AdvertiseEntity> {

    PageUtils<AdvertiseVo> getListPage(Integer current, Integer size, Integer position);

    void saveAdvertise(AdvertiseTo advertise);

    void updateAdvertise(AdvertiseTo advertise);
}
