package com.aidoudong.config.filter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 请求资源前，先通过此过滤器进行用户信息解析和叫校验 转发
 */
@Component
public class AuthenticationFilter extends ZuulFilter {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 如果解析到令牌就会封装到OAuth2Authentication对象
		if(!(authentication instanceof OAuth2Authentication)) {
			return null;
		}
		
		logger.info("网关获取到的认证对象："+authentication);
		// 用户名，没有其他信息 
		Object principal = authentication.getPrincipal();
		// 获取用户所拥有的权限
		Collection<? extends GrantedAuthority> authorities = 
				authentication.getAuthorities();
		Set<String> authoritySet = AuthorityUtils.authorityListToSet(authorities);
		// 请求详情
		Object details = authentication.getDetails();
		HashMap<String, Object> result = new HashMap<>();
		result.put("principal", principal);
		result.put("authorities", authoritySet);
		result.put("detail", details);
		// 将用户信息和权限信息转成json，再通过base64进行编码 
		String base64 = Base64Utils.encodeToString(JSON.toJSONString(result).getBytes());
		// 获取当前请求上下文
		RequestContext currentContext = RequestContext.getCurrentContext();
		// 添加到请求头
		currentContext.addZuulRequestHeader("auth-token", base64);
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
