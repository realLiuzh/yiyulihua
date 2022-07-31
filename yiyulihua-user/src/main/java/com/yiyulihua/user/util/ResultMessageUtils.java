package com.yiyulihua.user.util;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yiyulihua.common.vo.MessageVo;
import com.yiyulihua.common.vo.ResultMessageVo;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author sunbo
 * @date 2022-07-27-18:04
 */

public class ResultMessageUtils {

    public static String toMessage(String fromUserId, MessageVo messageVo) {
        ResultMessageVo resultMessageVo = new ResultMessageVo();
        BeanUtils.copyProperties(messageVo, resultMessageVo);
        resultMessageVo.setFromUserId(fromUserId);

        return JSON.toJSONString(resultMessageVo);
    }
}
