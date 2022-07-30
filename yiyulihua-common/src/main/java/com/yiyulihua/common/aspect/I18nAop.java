package com.yiyulihua.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yiyulihua.common.i18n.TransApi;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Aspect
@Component
@Slf4j
// aop
public class I18nAop {

    private static final String APP_ID = "20220728001285767";
    private static final String SECURITY_KEY = "D5b5KiCTLJZW9MmRueCH";

    @Pointcut("@annotation(com.yiyulihua.common.annotation.I18n)")
    public void pointcut() {

    }

    // 后置通知对返回结果进行国际化处理
    @AfterReturning(pointcut = "pointcut()", returning = "obj")
    public Object doAfterReturning(Object obj) throws IllegalAccessException {
        fun(obj);
        return obj;
    }

    // 对对象进行国际化处理 适用于嵌套
    private void fun(Object obj) throws IllegalAccessException {
        if (obj == null) return;
        // 获取对象的属性
        Class<?> clazz = obj.getClass();

        // jdk
        if (clazz != null && clazz.getClassLoader() == null) {
            if (List.class.isAssignableFrom(clazz)) {
                for (Object o1 : (List) obj) {
                    fun(o1);
                }
            }
        } else if (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                // 处理对象的每个属性
                Object fieldobj = field.get(obj);
                if (fieldobj != null && String.class.isAssignableFrom(fieldobj.getClass())) {
                    field.set(obj, i18n(fieldobj));
                }
                fun(fieldobj);
            }
        }
    }

    private String i18n(Object obj) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String transResult = api.getTransResult(((String) obj), "zh", "en");
        // log.warn("transResult:{}", transResult);
        JSONObject jsonObject = null;
        try {
            // 个人版访问频率受限
            Thread.sleep(1500);
            jsonObject = JSON.parseObject(transResult).getJSONArray("trans_result").getJSONObject(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject.getString("dst");
    }
}
