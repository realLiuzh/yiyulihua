package com.yiyulihua.order.controller;

import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.PayTo;
import com.yiyulihua.order.service.AliPayService;
import com.yiyulihua.order.service.WorksOrderService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

/**
 * @author sunbo
 * @since 2022/09/19 17:05
 */

@RestController
@RequestMapping("/pay/ali-pay")
@Api("支付宝支付")
@Slf4j
@CrossOrigin
public class AliPayController {

    private final AliPayService aliPayService;


    @Autowired
    public AliPayController(AliPayService aliPayService) {
        this.aliPayService = aliPayService;
    }

    @ApiOperation(value = "下单支付页面接口调用", notes = "返回 html 形式的 form 表单,包含自动提交的脚本," +
            "将表单提交到 action 所指向的支付宝开放平台,为用户展示一个支付页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId",
                    value = "商品id",
                    paramType = "path",
                    required = true
            )
    })
    @PostMapping("/page/pay")
    public Result<?> tradePagePay(@RequestBody PayTo payTo) {
        // 支付宝开发平台接收 request 请求对象后
        // 会为开发者生成 html 形式的 form 表单,包含自动提交的脚本
        String formStr = aliPayService.tradeCreate(payTo);

        // 前端执行自动提交脚本,将表单提交到 action 所指向的支付宝开放平台,为用户展示一个支付页面
        return Result.success().setData(Collections.singletonMap("formStr", formStr));
    }


    /**
     * 接收支付异步通知,处理后续逻辑
     * <p>
     * @ RequestParam 接收表单信息
     */
    @PostMapping("/notify")
    public String tradeNotify(@RequestParam Map<String, String> params) {

        return aliPayService.verifyAndProcess(params);
    }
}
