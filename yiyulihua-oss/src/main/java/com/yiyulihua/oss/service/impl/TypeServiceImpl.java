package com.yiyulihua.oss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.po.TypeEntity;
import com.yiyulihua.common.vo.TypeListVo;
import com.yiyulihua.oss.mapper.TypeMapper;
import com.yiyulihua.oss.service.TypeService;
import lombok.extern.slf4j.Slf4j;
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
 * @since 2022-07-25
 */
@Slf4j
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, TypeEntity> implements TypeService {

    @Override
    public List<TypeListVo> getTypeList() {
        //查找一级分类
        QueryWrapper<TypeEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<TypeEntity> oneTypeList = baseMapper.selectList(wrapper);
        //查找二级分类
        QueryWrapper<TypeEntity> wrapper1 = new QueryWrapper<>();
        wrapper1.ne("parent_id", "0");
        List<TypeEntity> twoTypeList = baseMapper.selectList(wrapper1);


        List<TypeListVo> list = new ArrayList<>();
        oneTypeList.forEach(oneType -> {
            TypeListVo typeListVo = new TypeListVo();
            BeanUtils.copyProperties(oneType, typeListVo);
            list.add(typeListVo);

            twoTypeList.forEach(twoType -> {
                if (oneType.getId().equals(twoType.getParentId())) {
                    TypeListVo twoTypeListVo = new TypeListVo();
                    BeanUtils.copyProperties(twoType, twoTypeListVo);
                    typeListVo.getChildren().add(twoTypeListVo);
                }
            });
        });

        return list;
    }
}
