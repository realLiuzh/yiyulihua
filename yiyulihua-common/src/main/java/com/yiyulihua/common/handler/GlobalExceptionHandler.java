package com.yiyulihua.common.handler;

import com.yiyulihua.common.exception.ApiException;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

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
    @ExceptionHandler(value = ApiException.class)
    public R bizExceptionHandler(HttpServletRequest req, ApiException e) {
        log.error("发生业务异常!异常描述:{}", e.getErrorMsg());
        return R.error(e.getErrorCode(), e.getErrorMsg());
    }


    // 处理其他异常
    @ExceptionHandler(value = Exception.class)
    public R exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常!异常描述:", e);
        return R.error(ApiExceptionEnum.INTERNAL_SERVER_ERROR);
//        return R.error(ApiExceptionEnum.INTERNAL_SERVER_ERROR.getResultCode(),ApiExceptionEnum.INTERNAL_SERVER_ERROR.getResultMsg());
    }

    /**
     * body 参数校验异常处理
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        if (StringUtils.hasText(msg)) {
            return Result.error(ApiExceptionEnum.BODY_NOT_MATCH.getResultCode(), msg);
        }
        return Result.error(ApiExceptionEnum.BODY_NOT_MATCH);
    }

    /**
     * {@code @PathVariable} 和 {@code @RequestParam} 参数校验不通过时抛出的异常处理
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public Result<?> handleConstraintViolationException(ConstraintViolationException ex) {
        if (StringUtils.hasText(ex.getMessage())) {
            return Result.error(ApiExceptionEnum.BODY_NOT_MATCH.getResultCode(), ex.getMessage());
        }
        return Result.error(ApiExceptionEnum.BODY_NOT_MATCH);
    }

}