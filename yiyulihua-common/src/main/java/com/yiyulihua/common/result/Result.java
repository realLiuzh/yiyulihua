package com.yiyulihua.common.result;

import com.alibaba.fastjson.JSONObject;
import com.yiyulihua.common.exception.ApiExceptionEnum;
import com.yiyulihua.common.exception.BaseErrorInfoInterface;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("统一返回对象")
public class Result<T> {
    /**
     * 响应代码
     */
    @ApiModelProperty(value = "响应代码")
    private String code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String message;

    /**
     * 响应结果
     */
    @ApiModelProperty(value = "响应结果")
    private T data;

    public Result() {
        this.setCode(ApiExceptionEnum.SUCCESS.getResultCode());
        this.setMessage(ApiExceptionEnum.SUCCESS.getResultMsg());
    }

    public Result(BaseErrorInfoInterface errorInfo) {
        this.code = errorInfo.getResultCode();
        this.message = errorInfo.getResultMsg();
    }

    /**
     * 成功
     */
    public static Result<Object> success() {
        Result<Object> rb = new Result<>();
        rb.setCode(ApiExceptionEnum.SUCCESS.getResultCode());
        rb.setMessage(ApiExceptionEnum.SUCCESS.getResultMsg());
        return rb;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 成功
     */
    public Result<T> success(T data) {
        Result<T> rb = new Result<>();
        rb.setCode(ApiExceptionEnum.SUCCESS.getResultCode());
        rb.setMessage(ApiExceptionEnum.SUCCESS.getResultMsg());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static Result error(BaseErrorInfoInterface errorInfo) {
        Result rb = new Result();
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        return rb;
    }

    /**
     * 失败
     */
    public static Result error(String code, String message) {
        Result rb = new Result();
        rb.setCode(code);
        rb.setMessage(message);
        return rb;
    }

    /**
     * 失败
     */
    public static Result error(String message) {
        Result rb = new Result();
        rb.setCode("-1");
        rb.setMessage(message);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
