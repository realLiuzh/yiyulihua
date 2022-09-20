package com.yiyulihua.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.po.WorksOrderEntity;
import com.yiyulihua.common.to.PayTo;
import com.yiyulihua.order.service.AliPayService;
import com.yiyulihua.order.service.WorksOrderService;
import com.yiyulihua.order.util.OrderConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author sunbo
 * @since 2022/09/19 17:12
 */

@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    private final AlipayClient alipayClient;
    private final WorksOrderService worksOrderService;

    @Resource
    private Environment config;


    @Autowired
    public AliPayServiceImpl(AlipayClient alipayClient, WorksOrderService worksOrderService) {
        this.alipayClient = alipayClient;
        this.worksOrderService = worksOrderService;
    }

    @Override
    public String tradeCreate(PayTo payTo) {

        try {
            Long orderNo = payTo.getOrderNo();
            WorksOrderEntity worksOrder = worksOrderService.query()
                    .select("quote_price", "works_id", "order_title")
                    .eq("order_no", orderNo)
                    .one();

            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            // 异步通知路径
            request.setNotifyUrl("");
            //支付成功后跳转
            request.setReturnUrl(config.getProperty("alipay.return-url"));
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            bizContent.put("total_amount", worksOrder.getQuotePrice());
            bizContent.put("subject", worksOrder.getOrderTitle());
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

            request.setBizContent(bizContent.toString());
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                log.info("调用成功,返回结果===>" + response.getBody());
                return response.getBody();
            } else {
                log.error("调用失败,返回码===>" + response.getCode() + ", 返回描述===>" + response.getMsg());
                throw new ApiException("创建支付交易失败");
            }
        } catch (AlipayApiException e) {
            throw new ApiException("创建支付交易失败");
        }
    }

    @Override
    public String verifyAndProcess(Map<String, String> params) {
        log.info("支付通知正在执行");
        log.info("通知参数===>" + params);
        String result = "failure";

        try {
            // 异步通知验签
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature
                    .rsaCheckV1(params,
                            config.getProperty("alipay.alipay-public-key"),
                            AlipayConstants.CHARSET_UTF8,
                            AlipayConstants.SIGN_TYPE_RSA2);
            if (!signVerified) {
                // 验签失败记录异常日志，并在response中返回 failure.
                log.error("支付成功但异步通知验签失败!");
                return result;
            }
            // 异步验签成功
            log.info("支付成功异步通知验签成功!");

            // 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，
            //  校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure

            String outTradeNo = params.get("out_trade_no");
            WorksOrderEntity order = worksOrderService.query()
                    .select("id", "quote_price")
                    .eq("order_no", outTradeNo).one();

            //1.商家需要验证该通知数据中的 out_trade_no 是否为商家系统中创建的订单号。
            if (null == order) {
                log.error("订单不存在!");
                return result;
            }

            //2.判断 total_amount 是否确实为该订单的实际金额（即商家订单创建时的金额）。
            String total_amount = params.get("total_amount");
            if (order.getQuotePrice().compareTo(new BigDecimal(total_amount)) != 0) {
                log.error("金额校验失败");
                return result;
            }
            //3.校验通知中的 seller_id（或者 seller_email) 是否为 out_trade_no
            // 这笔单据的对应的操作方（有的时候，一个商家可能有多个 seller_id/seller_email）。
            String seller_id = params.get("seller_id");
            if (!seller_id.equals(config.getProperty("alipay.seller-id"))) {
                log.error("商户 pid 校验失败!");
                return result;
            }

            //4.验证 app_id 是否为该商家本身。
            String app_id = params.get("app_id");
            if (!app_id.equals(config.getProperty("alipay.app-id"))) {
                log.error("商户 app_id 校验失败!");
                return result;
            }

            //。在支付宝的业务通知中，只有交易通知状态为 TRADE_SUCCESS(支持退款) 或 TRADE_FINISHED 时，
            // 支付宝才会认定为买家付款成功。
            String trade_status = params.get("trade_status");
            if (!"TRADE_SUCCESS".equals(trade_status)) {
                log.error("支付未成功!");
                return result;
            }

            //校验成功后在response返回success并继续商户自身业务,校验失败返回failure
            // 处理业务 修改订单状态 记录支付日志

            processOrder(params);
            // 向支付宝返回成功结果
            result = "success";
            return result;
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理订单
     *
     * @param params
     */
    @Override
    public void processOrder(Map<String, String> params) {
        log.info("处理订单");

        String orderNo = params.get("out_trade_no");

        // 更新订单状态
        worksOrderService.updateOrderStatusByOrderNo(orderNo, OrderConstants.PAID);

    }
}
