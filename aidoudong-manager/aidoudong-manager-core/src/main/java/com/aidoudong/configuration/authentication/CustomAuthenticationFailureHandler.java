package com.aidoudong.configuration.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aidoudong.common.ResultView;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.aidoudong.properties.LoginResponseType;
import com.aidoudong.properties.SecurityProperties;

@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Autowired
	SecurityProperties securityProperties;
	/**
	 * @Param AuthenticationException exception 认证失败时的异常
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		if(LoginResponseType.JSON.equals(
				securityProperties.getAuthentication().getLoginType())) {
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			// 认证失败响应json字符串
			response.getWriter().write(JSONObject.toJSONString(ResultView.fail(HttpStatus.UNAUTHORIZED.value(),exception.getMessage())));
		}else {
//			super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage()+"?error");
			// 获取上一次请求路径
			String referer = request.getHeader("referer");
			logger.info("referer："+referer);
			
			// 如果下面有值,则认为是多端登录,直接返回一个登录地址
			Object toAuthentication = request.getAttribute("toAuthentication");
			String lastUrl = toAuthentication != null 
					? securityProperties.getAuthentication().getLoginPage() 
					: StringUtils.substringBefore(referer, "?");
			
			logger.info("上一次请求的路径："+lastUrl);
			super.setDefaultFailureUrl(lastUrl+"?error");
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
