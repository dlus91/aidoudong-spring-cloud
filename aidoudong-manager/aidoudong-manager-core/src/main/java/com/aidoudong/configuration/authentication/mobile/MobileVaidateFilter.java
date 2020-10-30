package com.aidoudong.configuration.authentication.mobile;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aidoudong.configuration.authentication.CustomAuthenticationFailureHandler;
import com.aidoudong.configuration.authentication.exception.ValidateCodeException;
import com.aidoudong.controller.MobileLoginController;

/**
 * 校验用户输入的手机验证码是否正确
 */
@Component("mobileVaidateFilter")
public class MobileVaidateFilter extends OncePerRequestFilter {
	
	@Autowired
	CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 1, 判断请求是否为手机登录,且post请求
		if("/mobile/form".equals(request.getRequestURI()) && 
			"POST".equalsIgnoreCase(request.getMethod())) {
			try {
				// 校验验证码合法性
				validate(request);
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
		String sessionCode = (String) request.getSession().getAttribute(MobileLoginController.SESSION_KEY);
		// 获取用户输入的验证码
		String inputCode = request.getParameter("code");
		// 判断是否正确
		if(StringUtils.isEmpty(inputCode)) {
			throw new ValidateCodeException("手机验证码不能为空");
		}
		if(!inputCode.equalsIgnoreCase(sessionCode)) {
			throw new ValidateCodeException("手机验证码输入错误");
		}
	}

}
