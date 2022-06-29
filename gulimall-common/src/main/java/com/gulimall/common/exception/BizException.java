package com.gulimall.common.exception;

public class BizException extends RuntimeException {
	private static final long serialVersionUID = -1869693366962160250L;

	String	code;
	String	text;

	public BizException() {
		this("1", "Internal Error.");
	}

	public BizException(String msg) {
		super(msg);
		int pos = msg.indexOf("|");
		if (pos != -1) {
			this.code = msg.substring(0, pos);
			this.text = msg.substring(pos + 1);
		} else {
			this.code = "";
			this.text = msg;
		}
	}

	public BizException(String code, String text) {
		super(code + "|" + text);
		this.code = code;
		this.text = text;
	}

	public BizException(java.util.List<ErrorItem> errors) {
		super(errors.get(0).getCode() + "|" + errors.get(0).getMessage());
		if (errors.size() == 0) return;
		this.code = errors.get(0).getCode();
		this.text = errors.get(0).getMessage();
	}

	public String getCode() {
		return code;
	}

	public String getText() {
		return text;
	}

}
