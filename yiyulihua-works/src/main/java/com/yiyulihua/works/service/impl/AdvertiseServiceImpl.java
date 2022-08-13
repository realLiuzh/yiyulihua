package com.yiyulihua.works.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xiaoymin.knife4j.core.extend.OpenApiExtendMarkdownChildren;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.po.AdvertiseEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.to.AdvertiseTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;
import com.yiyulihua.common.vo.AdvertiseVo;
import com.yiyulihua.works.dao.AdvertiseDao;
import com.yiyulihua.works.service.AdvertiseService;
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
 * @since 2022-08-13
 */
@Service
public class AdvertiseServiceImpl extends ServiceImpl<AdvertiseDao, AdvertiseEntity> implements AdvertiseService {

    @Override
    public PageUtils<AdvertiseVo> getListPage(Integer current, Integer size, Integer position) {
        QueryWrapper<AdvertiseEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "cover_url", "link_url", "position", "sort");
        AssertUtil.isTrue(position == null || (position != 0 && position != 1), new ApiException(ApiExceptionEnum.BODY_NOT_MATCH));
        wrapper.eq("position", position);
        wrapper.orderByDesc("sort");

        Page<AdvertiseEntity> page = new Query<AdvertiseEntity>().getPage(new PageQuery(current, size));
        baseMapper.selectPage(page, wrapper);

        List<AdvertiseEntity> records = page.getRecords();
        List<AdvertiseVo> list = new ArrayList<>();
        records.forEach(record -> {
            AdvertiseVo advertiseVo = new AdvertiseVo();
            BeanUtils.copyProperties(record, advertiseVo);
            list.add(advertiseVo);
        });

        return new PageUtils<>(list, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Override
    public void saveAdvertise(AdvertiseTo advertise) {
        AdvertiseEntity advertiseEntity = new AdvertiseEntity();
        BeanUtils.copyProperties(advertise, advertiseEntity);
        int insert = baseMapper.insert(advertiseEntity);
        AssertUtil.isTrue(insert < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }

    @Override
    public void updateAdvertise(AdvertiseTo advertise) {
        AdvertiseEntity advertiseEntity = new AdvertiseEntity();
        BeanUtils.copyProperties(advertise, advertiseEntity);
        int update = baseMapper.updateById(advertiseEntity);
        AssertUtil.isTrue(update < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }


}
