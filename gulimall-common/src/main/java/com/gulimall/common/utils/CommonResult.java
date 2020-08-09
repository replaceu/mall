package com.gulimall.common.utils;

import com.gulimall.common.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-07-27
 */
@Getter
@Setter
public class CommonResult<T> {
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 转态码
     * 状态 0 为 成功 其他为失败
     */
    private Integer code;
    /**
     * 返回的数据
     */
    private T data;

    private CommonResult(String msg, Integer code , T data) {
        this.msg = msg;
        this.code = code;
        this.data = data ;
    }
    public static <T> CommonResult<T> ok() {
        return new CommonResult<T>("success", 0 , null  );
    }

    public static <T> CommonResult<T> ok(T data) {
        return new CommonResult<T>("success", 0 , data );
    }

    public static <T> CommonResult<T> ok(String msg) {
        return new CommonResult<T>(msg, 0 , null );
    }
    public static <T> CommonResult<T> ok(String msg , T data) {
        return new CommonResult<T>(msg, 0 , data);
    }

    public static <T> CommonResult<T> fail() {
        return new CommonResult<T>("fail", 1 , null );
    }
    public static <T> CommonResult<T> fail(String msg) {
        return new CommonResult<T>(msg, 1 , null );
    }
    public static <T> CommonResult<T> fail(int code , String msg) {
        return new CommonResult<T>(msg, code , null );
    }

    public static <T> CommonResult<T> fail(ErrorCode errorCode) {
        return new  CommonResult<T>(errorCode.getMsg() , errorCode.getCode() , null );
    }

    public CommonResult<T> data(T data) {
        this.data = data ;
        return this ;
    }
}