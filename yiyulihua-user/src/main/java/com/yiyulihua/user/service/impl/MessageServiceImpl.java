package com.yiyulihua.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.to.MessageDeleteTo;
import com.yiyulihua.common.to.MessageDeleteUserTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;
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
                resultMsg.setId(message.getId());
                resultMsg.setContent(message.getContent());
                resultMsg.setReceiveUserId(message.getReceiveUserId());
                resultMsg.setSendTime(message.getCreateTime());
                resultMsg.setFromUserId(message.getSendUserId());
                resultMsg.setIsSystem(message.getIsSystem());

                list.add(resultMsg);
            }
        }
        return list;
    }

    @Override
    public void updateOfflineStatus(String receiveUserId) {
        UpdateWrapper<MessageEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("receive_user_id", receiveUserId).set("is_offline", 0);
        int update = baseMapper.update(null, wrapper);
        AssertUtil.isTrue(update < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }

    @Override
    public PageUtils<ResultMessageVo> getHistoryMessagePage(Integer current, Integer size, String id) {

        Page<ResultMessageVo> page = new Query<ResultMessageVo>().getPage(new PageQuery(current, size));
        //自定义分页查询
        baseMapper.getHistoryMsgPageByUserId(page, id);

        return new PageUtils<>(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Override
    public void deleteRecords(MessageDeleteTo deleteVo) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(deleteVo.getMsgId());

        int update;
        if (deleteVo.getRole().equals(0)) {
            messageEntity.setSendUserVisible(1);
        } else {
            messageEntity.setReceiveUserVisible(1);
        }

        update = baseMapper.updateById(messageEntity);
        AssertUtil.isTrue(update < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));

    }

    @Override
    public void deleteRecordsBatch(MessageDeleteTo[] deleteVos) {
        for (MessageDeleteTo deleteVo : deleteVos) {

            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setId(deleteVo.getMsgId());

            if (deleteVo.getRole().equals(0)) {
                messageEntity.setSendUserVisible(1);
            } else {
                messageEntity.setReceiveUserVisible(1);
            }

            AssertUtil.isTrue(baseMapper.updateById(messageEntity) < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
        }
    }

    @Override
    public void deleteRecordsBetweenUser(MessageDeleteUserTo deleteUserTo) {
        // 为发送方的情况
        UpdateWrapper<MessageEntity> wrapper1 = new UpdateWrapper<>();
        wrapper1.eq("send_user_id", deleteUserTo.getUserId())
                .eq("receive_user_id", deleteUserTo.getToUserId())
                .set("send_user_visible", 1);

        AssertUtil.isTrue(baseMapper.update(null, wrapper1) < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));

        // 为接收方的情况
        UpdateWrapper<MessageEntity> wrapper2 = new UpdateWrapper<>();
        wrapper2.eq("receive_user_id", deleteUserTo.getUserId())
                .eq("send_user_id", deleteUserTo.getToUserId())
                .set("receive_user_visible", 1);
        AssertUtil.isTrue(baseMapper.update(null, wrapper2) < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }
}