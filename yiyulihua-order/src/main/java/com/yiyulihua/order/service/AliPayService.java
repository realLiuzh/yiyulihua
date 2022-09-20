package com.yiyulihua.order.service;

import com.yiyulihua.common.to.PayTo;

import java.util.Map;

/**
 * @author sunbo
 * @since 2022/09/19 17:11
 */
public interface AliPayService {

    String tradeCreate(PayTo payTo);

    String verifyAndProcess(Map<String, String> params);

    void processOrder(Map<String, String> params);
}
