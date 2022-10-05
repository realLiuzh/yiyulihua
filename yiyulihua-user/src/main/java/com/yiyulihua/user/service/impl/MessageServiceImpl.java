package com.yiyulihua.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.po.UserEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.to.HistoryMessageTo;
import com.yiyulihua.common.to.MessageDeleteTo;
import com.yiyulihua.common.utils.AssertUtil;
import com.yiyulihua.common.utils.DateUtils;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.Query;
import com.yiyulihua.common.vo.HistoryMessageVo;
import com.yiyulihua.common.vo.IndexMsgVo;
import com.yiyulihua.common.vo.ResultMessageVo;
import com.yiyulihua.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyulihua.user.dao.MessageDao;
import com.yiyulihua.user.service.MessageService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

    private final UserService userService;

    @Autowired
    public MessageServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<ResultMessageVo> getOfflineMessage(Integer receiveUserId) {
        List<ResultMessageVo> list = query()
                .eq("receive_user_id", receiveUserId)
                .eq("is_offline", 1)
                .orderByAsc("create_time")
                .list()
                .stream()
                .map(message -> {
                    ResultMessageVo resultMsg = new ResultMessageVo();
                    BeanUtils.copyProperties(message, resultMsg);
                    resultMsg.setSendTime(message.getCreateTime());
                    resultMsg.setFromUserId(message.getSendUserId());
                    return resultMsg;
                })
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public void updateOfflineStatus(Integer receiveUserId) {
        boolean update = update()
                .set("is_offline", 0)
                .eq("receive_user_id", receiveUserId)
                .update();
        AssertUtil.isTrue(!update, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }

    @Override
    public PageUtils<IndexMsgVo> getHistoryMessagePage(Integer current, Integer size) {
        // 根据 token 获取用户id
        int userId = StpUtil.getLoginIdAsInt();

        Page<ResultMessageVo> page = new Query<ResultMessageVo>().getPage(new PageQuery(current, size));
        //自定义分页查询
        baseMapper.getHistoryMsgPageByUserId(page, userId);
        List<ResultMessageVo> records = page.getRecords();

        // 获取聊天者头像
        List<IndexMsgVo> msgVos = records.stream().map(record -> {
            Integer fromUserId = record.getFromUserId();
            Integer receiveUserId = record.getReceiveUserId();
            Integer otherId = !fromUserId.equals(userId) ? fromUserId : receiveUserId;
            UserEntity one = userService.query()
                    .select("id", "avatar")
                    .eq("id", otherId)
                    .one();

            IndexMsgVo msgVo = BeanUtil.copyProperties(record, IndexMsgVo.class);
            msgVo.setAvatar(one.getAvatar());
            return msgVo;
        }).collect(Collectors.toList());


        return new PageUtils<>(msgVos, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
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
    public void deleteRecordsBetweenUser(Integer toUserId) {
        // 根据 token 获取用户id
        int userId = StpUtil.getLoginIdAsInt();

        // 为发送方的情况
        UpdateWrapper<MessageEntity> wrapper1 = new UpdateWrapper<>();
        wrapper1.eq("send_user_id", userId)
                .eq("receive_user_id", toUserId)
                .set("send_user_visible", 1);

        AssertUtil.isTrue(baseMapper.update(null, wrapper1) < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));

        // 为接收方的情况
        UpdateWrapper<MessageEntity> wrapper2 = new UpdateWrapper<>();
        wrapper2.eq("receive_user_id", userId)
                .eq("send_user_id", toUserId)
                .set("receive_user_visible", 1);
        AssertUtil.isTrue(baseMapper.update(null, wrapper2) < 1, new ApiException(ApiExceptionEnum.INTERNAL_SERVER_ERROR));
    }

    @Override
    public PageUtils<HistoryMessageVo> getHistoryMessageBetweenUserPage(HistoryMessageTo historyMessageTo) {
        // 根据 token 获取用户id
        int userId = StpUtil.getLoginIdAsInt();

        Page<HistoryMessageVo> page = new Query<HistoryMessageVo>().getPage(new PageQuery(historyMessageTo.getCurrent(), historyMessageTo.getSize()));
        // 设置时间区间
        String begin = null != historyMessageTo.getBegin() ? DateUtils.format(historyMessageTo.getBegin(), DateUtils.DATE_TIME_PATTERN) : "2022-01-01 00:00:00";
        String end = null != historyMessageTo.getEnd() ? DateUtils.format(historyMessageTo.getEnd(), DateUtils.DATE_TIME_PATTERN) : DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);

        baseMapper.getHistoryMessageBetweenPage(page, userId, historyMessageTo.getToUserId(), begin, end);

        return new PageUtils<>(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }
}