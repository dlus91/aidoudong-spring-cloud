package com.aidoudong.configuration.authentication.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码相关异常类
 */
public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = -3135156389119588451L;

	public ValidateCodeException(String msg) {
		super(msg);
	}
	
	public ValidateCodeException(String msg, Throwable t) {
		super(msg, t);
	}

}
