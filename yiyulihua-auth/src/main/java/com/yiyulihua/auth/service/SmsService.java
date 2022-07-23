package com.yiyulihua.auth.service;

/**
 * 短信业务
 */
public interface SmsService {

    /**
     *  发送验证码
     */
    boolean sendSms(String phone, String captcha);

}
