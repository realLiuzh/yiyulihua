package com.yiyulihua.order.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author sunbo
 * @date 2022/08/14 11:34
 */
public class OrderNoGenerator {

    /**
     * 生成8位订单号
     * Id组成：纯8位随机数字
     */
    public static String uuid8() {
        try {
            return String.valueOf(IdWorker.getFlowIdWorkerInstance().nextId()).substring(0, 8);
        } catch (Exception e) {
            Random random = new Random(System.currentTimeMillis());
            String tmp = "";
            for (int i = 0; i < 8; i++) {
                tmp += random.nextInt(10);
            }
            return tmp;
        }
    }

    /**
     * 生成16位订单号
     * Id组成：yyMMdd + (10位随机数字)
     */
    public static String uuid16() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            return simpleDateFormat.format(new Date(System.currentTimeMillis())).substring(2, 8) + String.valueOf(IdWorker.getFlowIdWorkerInstance().nextId()).substring(0, 10);
        } catch (Exception e) {
            Random random = new Random(System.currentTimeMillis());
            String tmp = "";
            for (int i = 0; i < 16; i++) {
                tmp += random.nextInt(10);
            }
            return simpleDateFormat.format(new Date(System.currentTimeMillis())).substring(2, 8) + tmp.substring(0, 10);
        }
    }

    /**
     * 生成32位订单号
     * Id组成：yyyyMMddHHmmss + (18位随机数字)
     */
    public static String uuid32() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return simpleDateFormat.format(new Date(System.currentTimeMillis())) + IdWorker.getFlowIdWorkerInstance().nextId();
        } catch (Exception e) {
            Random random = new Random(System.currentTimeMillis());
            String tmp = "";
            for (int i = 0; i < 18; i++) {
                tmp += random.nextInt(10);
            }
            return simpleDateFormat.format(new Date(System.currentTimeMillis())) + tmp;
        }
    }


    /**
     * 生成64位订单号
     * Id组成：yyyyMMddHHmmssSSS + (47位随机数字)
     */
    public static String uuid64() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        try {
            String tmp = simpleDateFormat.format(new Date(System.currentTimeMillis())) + IdWorker.getFlowIdWorkerInstance().nextId() + IdWorker.getFlowIdWorkerInstance().nextId() + IdWorker.getFlowIdWorkerInstance().nextId();
            return tmp.substring(0, 100);
        } catch (Exception e) {
            Random random = new Random(System.currentTimeMillis());
            String tmp = "";
            for (int i = 0; i < 47; i++) {
                tmp += random.nextInt(10);
            }
            return simpleDateFormat.format(new Date(System.currentTimeMillis())) + tmp;
        }
    }
}
