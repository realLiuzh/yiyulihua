package com.yiyulihua.common.exception;

// 异常处理枚举类
public enum ApiExceptionEnum implements BaseErrorInfoInterface {

    SUCCESS("0", "操作成功!"),
    INTERNAL_SERVER_ERROR("5000", "服务器内部错误!"),
    BODY_NOT_MATCH("4000", "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("4001", "请求的数字签名不匹配!"),
    NOT_FOUND("4004", "未找到该资源!"),
    SERVER_BUSY("5003", "服务器正忙，请稍后再试!"),
    // auth异常
    LOGIN_ERROR("1000", "邮箱或者密码错误"),
    MESSAGE_ERROR("1001", "短信验证码发送失败"),
    PHONE_USED("1002","手机号已被占用"),
    CODE_ERROR("1003","验证码错误"),
    REGISTER_ERROR("1004","注册失败，请联系管理员"),
    EMAIL_USED("1005","邮箱已被占用"),
    //任务提交
    IS_COMMITTED("2000","您已经提交任务");


    /**
     * 错误码
     */
    private final String resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    ApiExceptionEnum(String resultCode, String resultMsg) {
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