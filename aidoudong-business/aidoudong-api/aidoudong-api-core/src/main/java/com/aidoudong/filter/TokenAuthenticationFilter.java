package com.aidoudong.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 获取网关转发过来的请求头中保存的明文token值，用户信息
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String authToken = request.getHeader("auth-token");
		if(StringUtils.isNotEmpty(authToken)) {
			logger.info("商品资源服务器获取到的token值："+authToken);
			// 解析token
			// 1，通过base64解码
			String authTokenJson = new String(Base64Utils.decodeFromString(authToken));
			// 2，转成json对象
			JSONObject jsonObject = JSON.parseObject(authTokenJson);
			// 用户信息（用户名）
			Object principal = jsonObject.get("principal");
			// 请求详情
			Object detail = jsonObject.get("detail");
			// 用户拥有的权限 sys:user:list,sys:role:list
			String authorities = ArrayUtils.toString(jsonObject.getJSONArray("authorities").toArray());
			List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
			
			// 自己构建一个Authentication对象，SpringSecurity就会自动进行权限判断
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(
							principal, null, authorityList);
			authenticationToken.setDetails(detail);
			
			//将对象传给安全上下文，对应的就会自动的进行权限判断，同时也可以获取用户信息
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
		
	}

}
