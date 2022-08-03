package com.yiyulihua.auth.controller;

import com.yiyulihua.auth.service.SmsService;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.utils.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Api(value = "SmsApiController", tags = {"短信验证码发送"})
public class SmsApiController {

    @Resource
    private SmsService smsService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @ApiOperation(value = "短信发送6位验证码", notes = "Swagger调试不了这个接口,参数格式为form-data。验证码15分钟有效")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",
                    value = "手机号",
                    required = true)
    })
    @PostMapping("/send/phone")
    public Result<Object> sendSms(String phone) {
        String code = generateCaptcha();
        //手机号码格式：国家码+手机号 例如+8615213130124
        AssertUtil.isTrue(!smsService.sendSms("86".concat(phone), code), new ApiException(ApiExceptionEnum.MESSAGE_ERROR));
        //存到redis中
        redisTemplate.opsForValue().set(phone, code, 15, TimeUnit.MINUTES);
        return new Result<>().setData(code);
    }

    /**
     * 生成6位的验证码
     */
    private String generateCaptcha() {
        Random random = new Random();
        return String.valueOf(random.nextInt(900000) + 100000);
    }
}
