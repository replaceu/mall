package com.gulimall.common.exception;

/**
 * 自定义异常
 * @author aqiang9  2020-07-28
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
	private ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public BusinessException() {
		super();
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
