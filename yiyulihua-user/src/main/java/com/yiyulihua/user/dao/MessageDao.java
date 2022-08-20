package com.yiyulihua.user.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.vo.HistoryMessageVo;
import com.yiyulihua.common.vo.ResultMessageVo;
import com.yiyulihua.common.vo.WorksListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-30 10:38:30
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {
    /**
     * description: 分页查询历史消息
     *
     * @param page 分页插件
     * @author sunbo
     */
    IPage<ResultMessageVo> getHistoryMsgPageByUserId(IPage<ResultMessageVo> page,
                                                     @Param("userId") String userId);

    IPage<HistoryMessageVo> getHistoryMessageBetweenPage(IPage<HistoryMessageVo> page,
                                                         @Param("userId") String userId,
                                                         @Param("toUserId") String toUserId,
                                                         @Param("begin") String begin,
                                                         @Param("end") String end);
}