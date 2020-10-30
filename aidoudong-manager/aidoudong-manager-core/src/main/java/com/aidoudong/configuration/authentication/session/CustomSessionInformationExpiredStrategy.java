package com.aidoudong.configuration.authentication.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.aidoudong.configuration.authentication.CustomAuthenticationFailureHandler;

/**
 * 当同一用户的session达到指定数量时,会执行该类
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy{
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		// 1,获取用户名 
		UserDetails userDetails = 
				(UserDetails) event.getSessionInformation().getPrincipal();
		
		AuthenticationException exception = 
				new AuthenticationServiceException(String.format("[%s] 用户在另外一台电脑登录,您已下线", userDetails.getUsername()));
		
		event.getRequest().setAttribute("toAuthentication", true);
		customAuthenticationFailureHandler
			.onAuthenticationFailure(event.getRequest(), event.getResponse(), exception);
		
	}

}
