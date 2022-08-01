package com.yiyulihua.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.vo.ResultMessageVo;
import org.springframework.stereotype.Service;

import com.yiyulihua.user.dao.MessageDao;
import com.yiyulihua.user.service.MessageService;

import java.util.ArrayList;
import java.util.List;


@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {


    @Override
    public List<ResultMessageVo> getOfflineMessage(String receiveUserId) {
        List<ResultMessageVo> list = new ArrayList<>();

        QueryWrapper<MessageEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("receive_user_id", receiveUserId);
        wrapper.eq("is_offline", 1);
        wrapper.orderByAsc("create_time");

        List<MessageEntity> messages = baseMapper.selectList(wrapper);
        if (messages != null) {
            for (MessageEntity message : messages) {
                ResultMessageVo resultMsg = new ResultMessageVo();
                resultMsg.setMessage(message.getContent());
                resultMsg.setSendTime(message.getCreateTime());
                resultMsg.setFromUserId(message.getSendUserId());
                if (message.getIsSystem() == 1) {
                    resultMsg.setSystemMsg(true);
                }
                list.add(resultMsg);
            }
        }
        return list;
    }

    @Override
    public void updateOfflineStatus(String userId) {
        UpdateWrapper<MessageEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("receive_user_id", userId).set("is_offline", 0);
        int update = baseMapper.update(null, wrapper);
        AssertUtil.isTrue(update < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }

}