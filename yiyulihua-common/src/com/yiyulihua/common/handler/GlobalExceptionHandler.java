package com.yiyulihua.common.handler;

import com.yiyulihua.common.exception.CustomException;
import com.yiyulihua.common.exception.ExceptionEnum;
import com.yiyulihua.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

// 自定义异常处理
@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    public R bizExceptionHandler(HttpServletRequest req, CustomException e) {
        log.error("发生业务异常!异常描述:{}", e.getErrorMsg());
        return R.error(e.getErrorCode(), e.getErrorMsg());
    }


    // 处理其他异常
    @ExceptionHandler(value = Exception.class)
    public  R exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常!异常描述:", e);
        return R.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
    }
}