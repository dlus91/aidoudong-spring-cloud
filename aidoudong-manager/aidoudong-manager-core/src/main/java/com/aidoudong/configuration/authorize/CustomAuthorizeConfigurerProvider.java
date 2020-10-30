package com.aidoudong.configuration.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.aidoudong.properties.Authentication;
import com.aidoudong.properties.SecurityProperties;

/**
 * 身份认证相关的授权配置
 */
@Component
@Order(Integer.MAX_VALUE) //值越大加载优先级越后
public class CustomAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		Authentication authenticationProperties = securityProperties.getAuthentication();
		config
		.antMatchers(authenticationProperties.getLoginPage(),
				authenticationProperties.getImageCodeUrl(),
				authenticationProperties.getMobilePageUrl(),
				authenticationProperties.getMobileImageCodeUrl(),
				"/test"
		).permitAll() //放行/login/page不需要认证可访问
		.anyRequest().authenticated() //所有访问该应用的http请求都需要通过该认证
		;
	}

}
