package com.yiyulihua.common.exception;

// 异常处理枚举类
public enum ExceptionEnum implements BaseErrorInfoInterface {

    SUCCESS("0", "成功!"),
    INTERNAL_SERVER_ERROR("5000", "服务器内部错误!"),
    BODY_NOT_MATCH("4000", "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("4001", "请求的数字签名不匹配!"),
    NOT_FOUND("4004", "未找到该资源!"),
    SERVER_BUSY("5003", "服务器正忙，请稍后再试!"),
    // auth异常
    LOGIN_ERROR("1000", "邮箱或者密码错误");


    /**
     * 错误码
     */
    private final String resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    ExceptionEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}