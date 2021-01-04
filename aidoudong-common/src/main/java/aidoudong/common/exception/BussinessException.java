package aidoudong.common.exception;

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

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
