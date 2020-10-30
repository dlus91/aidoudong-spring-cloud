package com.aidoudong.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class TokenFeignClientInterceptor implements RequestInterceptor	{
	
	private final String AUTHORIZATION_HEADER = "Authorization";
	private final String BEARER_TOKEN_TYPE = "bearer";

	@Override
	public void apply(RequestTemplate template) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if(authentication != null && authentication.getDetails() instanceof JSONObject) {
			JSONObject detailJson = (JSONObject) authentication.getDetails();
			template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, detailJson.get("tokenValue")));
		}
		
	}

}
