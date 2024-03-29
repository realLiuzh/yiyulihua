package com.yiyulihua.works.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.po.WorksEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.query.WorksQuery;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.WorksPublishTo;
import com.yiyulihua.common.to.WorksUpdateTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;
import com.yiyulihua.common.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yiyulihua.works.dao.WorksDao;
import com.yiyulihua.works.service.WorksService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service("workService")
public class WorksServiceImpl extends ServiceImpl<WorksDao, WorksEntity> implements WorksService {

    @Override
    public PageUtils<WorksListVo> queryPage(WorksQuery worksQuery) {
        QueryWrapper<WorksListVo> wrapper = new QueryWrapper<>();

        // 类型查询条件
        if (!StringUtils.isEmpty(worksQuery.getTypeId())) {
            wrapper.eq("type_id", worksQuery.getTypeId());
            if (!StringUtils.isEmpty(worksQuery.getSubtypeId())) {
                wrapper.eq("subtype_id", worksQuery.getSubtypeId());
            }
        }
        // 筛选进行中或待确认的作品
        wrapper.in("works_status", 1, 2);
        // 排序条件
        sortOperation(wrapper, worksQuery);

        //分页查询
        Page<WorksListVo> page = new Query<WorksListVo>().getPage(new PageQuery(worksQuery.getCurrentPage(), worksQuery.getPageSize()));
        IPage<WorksListVo> iPage = baseMapper.getWorksList(page, wrapper);

        return new PageUtils<>(iPage.getRecords(), (int) iPage.getTotal(), (int) iPage.getSize(), (int) iPage.getCurrent());
    }

    private void sortOperation(QueryWrapper<WorksListVo> wrapper, WorksQuery worksQuery) {
        Integer timeSort = worksQuery.getTimeSort();
        Integer priceSort = worksQuery.getPriceSort();

        if (!isEmpty(timeSort) && !isEmpty(priceSort)) {
            if (Math.abs(timeSort) > Math.abs(priceSort)) {
                if (timeSort > 0) {
                    wrapper.orderByDesc("works_deadline");
                } else {
                    wrapper.orderByAsc("works_deadline");
                }
                if (priceSort > 0) {
                    wrapper.orderByDesc("works_price");
                } else {
                    wrapper.orderByAsc("works_price");
                }
            } else {
                if (priceSort > 0) {
                    wrapper.orderByDesc("works_price");
                } else {
                    wrapper.orderByAsc("works_price");
                }
                if (timeSort > 0) {
                    wrapper.orderByDesc("works_deadline");
                } else {
                    wrapper.orderByAsc("works_deadline");
                }
            }
        } else if (isEmpty(timeSort)) {
            if (timeSort > 0) {
                wrapper.orderByDesc("works_deadline");
            } else {
                wrapper.orderByAsc("works_deadline");
            }
        } else if (isEmpty(priceSort)) {
            if (priceSort > 0) {
                wrapper.orderByDesc("works_price");
            } else {
                wrapper.orderByAsc("works_price");
            }
        }
    }

    private boolean isEmpty(Integer i) {
        return i == null || i == 0;
    }

    @Override
    public WorksDetailsVo getDetailsInfoById(Integer id) {
        QueryWrapper<WorksDetailsVo> wrapper = new QueryWrapper<>();
        wrapper.eq("tb_works.id", id);
        WorksDetailsVo works = baseMapper.getWorksDetails(wrapper);
        if (works == null) {
            throw new ApiException(ApiExceptionEnum.NOT_FOUND);
        }
        return works;
    }

    @Override
    public void publishOrSave(WorksPublishTo work) {
        Object loginIdDefaultNull = StpUtil.getLoginIdDefaultNull();
        AssertUtil.isTrue(null == loginIdDefaultNull, new ApiException(ApiExceptionEnum.SIGNATURE_NOT_MATCH));
        String userId = loginIdDefaultNull.toString();

        WorksEntity worksEntity = new WorksEntity();
        worksEntity.setPublisherId(userId);
        BeanUtils.copyProperties(work, worksEntity);
        int insert = baseMapper.insert(worksEntity);
        AssertUtil.isTrue(insert < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }

    @Override
    public void updateInfoById(WorksUpdateTo work) {
        //检查参数
        if (StringUtils.isEmpty(work.getId())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }

        WorksEntity worksEntity = new WorksEntity();
        BeanUtils.copyProperties(work, worksEntity);
        int update = baseMapper.updateById(worksEntity);
        AssertUtil.isTrue(update < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }

    @Override
    public PageUtils<WorksMyPublishVo> getWorksByPublisherId(Integer current, Integer size) {
        // 根据 token 获取用户id
        int publisherId = StpUtil.getLoginIdAsInt();

        //条件
        QueryWrapper<WorksEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "works_name", "type", "subtype", "works_cover", "works_price", "works_bid_number", "works_status", "works_deadline");
        wrapper.eq("publisher_id", publisherId);
        //已发布的作品
        wrapper.ne("works_status", 0);
        //根据修改时间排序
        wrapper.orderByDesc("update_time");

        //分页查询
        Page<WorksEntity> page = new Query<WorksEntity>().getPage(new PageQuery(current, size));
        baseMapper.selectPage(page, wrapper);

        //封装数据
        List<WorksEntity> worksEntities = page.getRecords();
        List<WorksMyPublishVo> list = new ArrayList<>();
        worksEntities.forEach(worksEntity -> {
            WorksMyPublishVo worksMyPublishVo = new WorksMyPublishVo();
            BeanUtils.copyProperties(worksEntity, worksMyPublishVo);
            list.add(worksMyPublishVo);
        });

        return new PageUtils<>(list, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Override
    public List<WorksListVo> getRecommend(Integer id) {
        WorksEntity worksEntity = baseMapper.selectById(id);
        return baseMapper.getRecommend(worksEntity.getTypeId());
    }

    @Override
    public WorksPublishTo getOnlySaveInfo() {
        // 根据 token 获取用户id
        int publisherId = StpUtil.getLoginIdAsInt();

        QueryWrapper<WorksEntity> wrapper = new QueryWrapper<>();
        wrapper.select("works_name", "type_id", "type", "subtype_id", "subtype", "preview_url", "real_url", "works_cover", "works_demand", "works_price", "works_process", "remark", "works_status", "works_deadline");
        wrapper.eq("publisher_id", publisherId);
        wrapper.eq("works_status", 0);
        wrapper.orderByDesc("update_time");

        WorksEntity worksEntity = baseMapper.selectOne(wrapper);
        WorksPublishTo worksPublishTo = new WorksPublishTo();
        BeanUtils.copyProperties(worksEntity, worksPublishTo);

        return worksPublishTo;
    }

    @Override
    public Result<?> updateBinNumber(Integer workId) {
        boolean update = update()
                .setSql("works_bid_number = works_bid_number + 1")
                .eq("id", workId)
                .update();
        AssertUtil.isTrue(!update, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
        return Result.success();
    }
}