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
     * 状态 0 为 成功    1 为 失败
     */
    private int status ;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 转态码
     */
    private Integer code;
    /**
     * 返回的数据
     */
    private Object data;

    private CommonResult(int status , String msg, Integer code, Object data) {
        this.status = status ;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
    public static CommonResult ok() {

        return new CommonResult(0 ,"成功", 200,  null);
    }
    public static CommonResult ok(String msg) {
        return new CommonResult(0,msg, 500,  null);
    }
    public static CommonResult fail() {
        return new CommonResult(1,"失败", 500,  null);
    }
    public static CommonResult fail(String msg) {
        return new CommonResult(1,msg, 500, null);
    }
    public static CommonResult fail(int code , String msg) {
        return new CommonResult(1,msg, code, null);
    }
    public static CommonResult fail(ErrorCode errorCode) {
        return new CommonResult(1,errorCode.getMsg() , errorCode.getCode() , null);
    }

    public CommonResult data(Object data) {
        this.data = data ;
        return this ;
    }
}