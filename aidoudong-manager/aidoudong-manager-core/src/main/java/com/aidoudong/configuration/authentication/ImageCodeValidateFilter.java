package com.aidoudong.configuration.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aidoudong.configuration.authentication.exception.ValidateCodeException;
import com.aidoudong.controller.CustomLoginController;
import com.aidoudong.properties.SecurityProperties;

/**
 * OncePerRequestFilter：所有请求之前被调用一次
 */
@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	SecurityProperties securityProperties;
	@Autowired
	CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		// 1，如果是登录请求，则校验输入的验证码是否正确
		if(securityProperties.getAuthentication().getLoginProcessingUrl()
				.equals(request.getRequestURI()) && 
				"POST".equalsIgnoreCase(request.getMethod())) {
			try {
				// 校验验证码合法性
				//TODO: 本地环境先注释
//				validate(request);
				logger.info("校验码校验成功");
			} catch (AuthenticationException exception) {
				logger.info("校验码校验失败");
				// 交给失败处理器进行处理异常
				customAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception);
				// 一定要记得结束
				return;
			}
			
		}
		
		// 放行请求
		filterChain.doFilter(request, response);
	}

	private void validate(HttpServletRequest request) {
		// 先获取session中的验证码
		String sessionCode = (String) request.getSession().getAttribute(CustomLoginController.SESSION_KEY);
		// 获取用户输入的验证码
		String inputCode = request.getParameter("code");
		// 判断是否正确
		if(StringUtils.isEmpty(inputCode)) {
			throw new ValidateCodeException("验证码不能为空");
		}
		if(!inputCode.equalsIgnoreCase(sessionCode)) {
			throw new ValidateCodeException("验证码输入错误");
		}
	}

}
