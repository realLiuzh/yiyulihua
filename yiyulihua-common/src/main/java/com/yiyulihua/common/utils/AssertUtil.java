package com.yiyulihua.common.utils;

// 断言工具类
public class AssertUtil {
    public static void isTrue(boolean flag, String message) {
        if (flag) throw new RuntimeException(message);
    }

    public static void isTrue(boolean flag, RuntimeException exception) {
        if (flag) throw exception;
    }
}
