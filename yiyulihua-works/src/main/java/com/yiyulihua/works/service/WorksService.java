package com.yiyulihua.works.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.po.WorksEntity;
import com.yiyulihua.common.query.WorksQuery;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.WorksDetailsVo;
import com.yiyulihua.common.vo.WorksListVo;
import com.yiyulihua.common.vo.WorksPublishVo;
import com.yiyulihua.common.vo.WorksUpdateVo;

/**
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-16 16:53:42
 */
public interface WorksService extends IService<WorksEntity> {

    /**
     * description: 根据查询条件分页查询作品信息
     *
     * @param worksQuery 查询条件
     * @return {@link PageUtils<WorksListVo>}
     * @author sunbo
     * @date 2022/7/23 19:22
     */
    PageUtils<WorksListVo> queryPage(WorksQuery worksQuery);

    /**
     * description: 根据作品id获取作品详细信息
     *
     * @param id 作品id
     * @return {@link WorksDetailsVo}
     * @author sunbo
     * @date 2022/7/23 19:21
     */
    WorksDetailsVo getDetailsInfoById(String id);

    /**
     * description: 保存或发布作品
     *
     * @param work 作品信息
     * @author sunbo
     * @date 2022/7/23 20:26
     */
    void publishOrSave(WorksPublishVo work);

    /**
     * description: 更新作品信息
     *
     * @param work 作品信息
     * @author sunbo
     * @date 2022/7/24 17:19
     */
    void updateInfoById(WorksUpdateVo work);
}

