package com.yiyulihua.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.to.HistoryMessageTo;
import com.yiyulihua.common.to.MessageDeleteTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.HistoryMessageVo;
import com.yiyulihua.common.vo.IndexMsgVo;
import com.yiyulihua.common.vo.ResultMessageVo;

import java.util.List;


/**
 * @author sunbo.2000
 * @since 2022/8/12 12:30
 */
public interface MessageService extends IService<MessageEntity> {

    List<ResultMessageVo> getOfflineMessage(Integer receiveUserId);

    void updateOfflineStatus(Integer userId);

    PageUtils<IndexMsgVo> getHistoryMessagePage(Integer current, Integer size);

    void deleteRecords(MessageDeleteTo deleteVo);

    void deleteRecordsBatch(MessageDeleteTo[] deleteVos);

    void deleteRecordsBetweenUser(Integer deleteUserTo);

    PageUtils<HistoryMessageVo> getHistoryMessageBetweenUserPage(HistoryMessageTo historyMessageTo);
}

