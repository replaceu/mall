package com.gulimall.common.utils;

import com.gulimall.common.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-07-27
 */
@Getter
@Setter
public class CommonResult {
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
    private Object data;

    private CommonResult(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
    public static CommonResult ok() {
        return new CommonResult("success", 0 );
    }
    public static CommonResult ok(String msg) {
        return new CommonResult(msg, 0);
    }

    public static CommonResult fail() {
        return new CommonResult("fail", 1);
    }
    public static CommonResult fail(String msg) {
        return new CommonResult(msg, 1);
    }
    public static CommonResult fail(int code , String msg) {
        return new CommonResult(msg, code);
    }


    public static CommonResult fail(ErrorCode errorCode) {
        return new CommonResult(errorCode.getMsg() , errorCode.getCode());
    }

    public CommonResult data(Object data) {
        this.data = data ;
        return this ;
    }
}