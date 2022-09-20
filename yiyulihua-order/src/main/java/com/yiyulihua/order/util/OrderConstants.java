package com.yiyulihua.order.util;


/**
 * @author sunbo
 * @since 2022/09/20 14:24
 */

public enum OrderConstants {

    /**
     * 未支付
     */
    UNPAID(0, "pay_status"),

    /**
     * 已支付
     */
    PAID(1, "pay_status"),

    /**
     * 已报价
     */
    QUOTED(0, "order_status"),

    /**
     * 被驳回
     */

    REJECTED(1, "order_status"),

    /**
     * 已完成
     */
    COMPLETED2(2, "order_status"),

    /**
     * 已购入
     */
    PURCHASED(3, "order_status");

    private final Integer code;
    private final String msg;

    OrderConstants(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
