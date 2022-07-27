package com.yiyulihua.user.util;


import com.alibaba.fastjson.JSON;
import com.yiyulihua.common.vo.ResultMessageVo;
import org.springframework.util.StringUtils;

/**
 * @author sunbo
 * @date 2022-07-27-18:04
 */

public class ResultMessageUtils {

    public static String toMessage(boolean isSystem, String fromUserId, Object message) {
        ResultMessageVo vo = new ResultMessageVo();
        vo.setSystem(isSystem);
        vo.setMessage(message);
        if (!StringUtils.isEmpty(fromUserId)) {
            vo.setFromUserId(fromUserId);
        }

        return JSON.toJSONString(vo);
    }
}
