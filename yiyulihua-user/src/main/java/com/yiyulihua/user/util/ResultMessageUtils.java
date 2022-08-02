package com.yiyulihua.user.util;


import com.alibaba.fastjson.JSON;
import com.yiyulihua.common.to.MessageTo;
import com.yiyulihua.common.vo.ResultMessageVo;
import org.springframework.beans.BeanUtils;

/**
 * @author sunbo
 * @date 2022-07-27-18:04
 */

public class ResultMessageUtils {

    public static String toMessage(String id, String fromUserId, MessageTo messageTo) {
        ResultMessageVo resultMessageVo = new ResultMessageVo();

        resultMessageVo.setId(id);
        resultMessageVo.setIsSystem(messageTo.getIsSystem());
        resultMessageVo.setFromUserId(fromUserId);
        resultMessageVo.setReceiveUserId(messageTo.getToUserId());
        resultMessageVo.setContent(messageTo.getContent());
        resultMessageVo.setSendTime(messageTo.getSendTime());

        System.out.println(resultMessageVo);

        return JSON.toJSONStringWithDateFormat(resultMessageVo, "yyyy-MM-dd HH:mm:ss");
    }
}
