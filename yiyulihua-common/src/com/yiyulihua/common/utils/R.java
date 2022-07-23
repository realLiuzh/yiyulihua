package com.yiyulihua.common.utils;


import com.yiyulihua.common.exception.BaseErrorInfoInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R<T> extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private T data;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public R() {
        put("msg", "success");
        put("code", 0);
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static R error(BaseErrorInfoInterface errorInfo) {
        R rb = new R();
        rb.put("code", errorInfo.getResultCode());
        rb.put("msg", errorInfo.getResultMsg());
        return rb;
    }

    public static R error(String code, String message) {
        R rb = new R();
        rb.put("code", code);
        rb.put("msg", message);
        return rb;
    }


    public static R error(String message) {
        R rb = new R();
        rb.put("code", 500);
        rb.put("msg", message);
        return rb;
    }
}
