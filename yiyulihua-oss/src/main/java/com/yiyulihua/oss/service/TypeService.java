package com.yiyulihua.oss.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.po.TypeEntity;
import com.yiyulihua.common.vo.TypeListVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-07-25
 */
@Service
public interface TypeService extends IService<TypeEntity> {

    /**
     * description: 查询所有类型
     *
     * @return {@link List<TypeListVo>}
     * @author sunbo
     * @date 2022/7/25 13:40
     */
    List<TypeListVo> getTypeList();
}
