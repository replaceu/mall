package com.gulimall.common.exception;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ErrorItem {
	@ApiModelProperty("错误CODE")
	private String code;

	@ApiModelProperty("错误TEXT")
	private String message;

	public ErrorItem() {
	}

	public ErrorItem(String text) {
		int pos = text.indexOf("|");
		this.code = pos == -1 ? "sysErr" : text.substring(0, pos);
		this.message = pos == -1 ? text : text.substring(pos + 1);
	}

	public ErrorItem(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static List<ErrorItem> newInstances(List<String> messages) {
		List<ErrorItem> errorItems = new ArrayList<>();
		if (messages == null || messages.size() == 0) return errorItems;
		//
		for (String message : messages) {
			errorItems.add(new ErrorItem(message));
		}
		return errorItems;
	}

}
