package com.aidoudong.common.exception;

public class BussinessException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String message;

	public BussinessException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public BussinessException(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
