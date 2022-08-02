package com.yiyulihua.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.to.MessageDeleteTo;
import com.yiyulihua.common.to.MessageDeleteUserTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.ResultMessageVo;

import java.util.List;


/**
 * 
 *
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-30 10:38:30
 */
public interface MessageService extends IService<MessageEntity> {

    List<ResultMessageVo> getOfflineMessage(String receiveUserId);

    void updateOfflineStatus(String userId);

    PageUtils<ResultMessageVo> getHistoryMessagePage(Integer current, Integer size, String id);

    void deleteRecords(MessageDeleteTo deleteVo);

    void deleteRecordsBatch(MessageDeleteTo[] deleteVos);

    void deleteRecordsBetweenUser(MessageDeleteUserTo deleteUserTo);
}

