package com.yiyulihua.works.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.po.WorksEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.query.WorksQuery;
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
        // 筛选已经布的作品
        wrapper.eq("works_status", 1);
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
    public WorksDetailsVo getDetailsInfoById(String id) {
        QueryWrapper<WorksDetailsVo> wrapper = new QueryWrapper<>();
        wrapper.eq("tb_works.id", id);
        WorksDetailsVo works = baseMapper.getWorksDetails(wrapper);
        if (works == null) {
            throw new ApiException(ApiExceptionEnum.NOT_FOUND);
        }
        return works;
    }

    @Override
    public void publishOrSave(WorksPublishVo work) {
        //检查参数
        checkEmpty(work);

        WorksEntity worksEntity = new WorksEntity();
        BeanUtils.copyProperties(work, worksEntity);
        int insert = baseMapper.insert(worksEntity);
        if (insert < 1) {
            if (work.getWorksStatus() == 0) {
                throw new ApiException("-1", "保存作品失败");
            } else {
                throw new ApiException("-1", "发布作品失败");
            }
        }
    }

    @Override
    public void updateInfoById(WorksUpdateVo work) {
        //检查参数
        if (StringUtils.isEmpty(work.getId())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }

        WorksEntity worksEntity = new WorksEntity();
        BeanUtils.copyProperties(work, worksEntity);
        int i = baseMapper.updateById(worksEntity);
        if (i < 1) {
            throw new ApiException("-1", "更新作品失败");
        }
    }

    @Override
    public PageUtils<WorksMyPublishVo> getWorksByPublisherId(Integer current, Integer size, String publisherId) {
        //条件
        QueryWrapper<WorksEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("publisher_id", publisherId);
        wrapper.orderByDesc("create_time");

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

        return new PageUtils<WorksMyPublishVo>(list, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    private void checkEmpty(WorksPublishVo work) {
        if (StringUtils.isEmpty(work.getWorksName())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
        if (StringUtils.isEmpty(work.getTypeId())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
        if (StringUtils.isEmpty(work.getType())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
        if (StringUtils.isEmpty(work.getSubtypeId())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
        if (StringUtils.isEmpty(work.getSubtype())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
        if (StringUtils.isEmpty(work.getPreviewUrl())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
        if (StringUtils.isEmpty(work.getRealUrl())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
        if (StringUtils.isEmpty(work.getWorksPrice())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
        if (StringUtils.isEmpty(work.getRemark())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
        if (StringUtils.isEmpty(work.getWorksStatus())) {
            throw new ApiException(ApiExceptionEnum.BODY_NOT_MATCH);
        }
    }
}