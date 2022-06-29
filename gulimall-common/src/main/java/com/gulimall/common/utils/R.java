/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.gulimall.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import io.swagger.annotations.ApiModelProperty;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("响应状态码")
	private Integer code;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public R() {
		put("code", 0);
		put("msg", "success");
	}

	public static R error() {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
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

	public R setData(Object data) {
		put("data", data);
		return this;
	}

	public <T> T getData(TypeReference<T> typeReference) {
		Object data = get("data");
		String s = JSON.toJSONString(data);
		T t = JSON.parseObject(s, typeReference);
		return t;
	}
}
