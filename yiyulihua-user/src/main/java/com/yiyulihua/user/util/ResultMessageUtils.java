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
        BeanUtils.copyProperties(messageTo, resultMessageVo);
        resultMessageVo.setId(id);
        resultMessageVo.setFromUserId(fromUserId);
        resultMessageVo.setReceiveUserId(messageTo.getToUserId());

        return JSON.toJSONString(resultMessageVo);
    }
}
