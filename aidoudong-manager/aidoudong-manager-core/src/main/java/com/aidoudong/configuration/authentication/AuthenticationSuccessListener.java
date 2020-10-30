package com.aidoudong.configuration.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

/**
 * 这个接口是用来监听认证成功后的处理，
 * 也就是说认证成功让认证成功处理器调用此方法（successListener）
 */
public interface AuthenticationSuccessListener {
	
	void successListener(HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication);
	
}
