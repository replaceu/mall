package com.gulimall.common.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-07-27
 */
@Getter
@Setter
public class Result {
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

    private Result(String msg, Integer code, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
    public static Result ok() {
        return new Result("成功", 0,  null);
    }
    public static Result ok(String msg) {
        return new Result(msg, 0,  null);
    }
    public static Result fail() {
        return new Result("失败", 1,  null);
    }
    public static Result fail(String msg) {
        return new Result(msg, 1, null);
    }
    public Result data(Object data) {
        this.data = data ;
        return this ;
    }
}