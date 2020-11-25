package com.aidoudong.configuration.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aidoudong.common.ResultView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.aidoudong.properties.LoginResponseType;
import com.aidoudong.properties.SecurityProperties;
import com.alibaba.fastjson.JSON;

/**
 * 认证成功处理器
 * 1，决定响应json还是跳转页面，或者认证成功后进行其他处理
 */

@Component("customAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	SecurityProperties securityProperties;
	@Autowired(required = false) //容器中可以不需要这个接口的实现，如果有则自动注入
	AuthenticationSuccessListener authenticationSuccessListener;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		if(authenticationSuccessListener != null) {
			// 当认证之后，调用此监听，进行后续处理，比如加载用户权限菜单 
			authenticationSuccessListener.successListener(request, response, authentication);
		}
		
		if(LoginResponseType.JSON.equals(
				securityProperties.getAuthentication().getLoginType())) {
			
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			// 认证成功后，响应json字符串
			response.getWriter().write(ResultView.success().outPutData());
		}else {
			// 认证成功后，重定向到上次请求的地址上，引发跳转到认证页面的地址
			logger.info("authentication："+ JSON.toJSONString(authentication));
			super.onAuthenticationSuccess(request, response, authentication);
		}

	}

}
