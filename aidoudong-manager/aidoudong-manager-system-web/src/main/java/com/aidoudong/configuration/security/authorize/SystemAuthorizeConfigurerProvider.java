package com.aidoudong.configuration.security.authorize;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.aidoudong.configuration.authorize.AuthorizeConfigurerProvider;

/**
 * 关于系统管理模块的授权配置
 */
@Component
public class SystemAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider {

	@Override
	public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		
		// antMatchers("/**") 表示所有 
		// hasRole/hasAnyRole设置角色时会加上ROLE_作为前缀 即 ROLE_ADMIN
//		.antMatchers("/user").hasRole("ADMIN")
		// hasAuthority/hasAnyAuthority设置角色时 不会 加上ROLE_作为前缀 即 ADMIN，ROOT
//		.antMatchers("/user").hasAnyAuthority("ADMIN","ROOT")
		// hasIpAddress 只有这个ip的用户才能访问到这些资源
//		.antMatchers("/user").hasIpAddress("192.168.1.100/150")
		// hasIpAddress 只有这些ip的用户才能访问到这些资源
//		.antMatchers("/user").access("hasIpAddress('127.0.0.1') or hasIpAddress('192.168.1.101/120') or hasIpAddress('0:0:0:0:0:0:0:1')")
//		.antMatchers("/user").access(
//		"hasIpAddress('127.0.0.1/10') or hasIpAddress('128.0.0.1/10')");
		
		
		
//		config
//			.antMatchers("/user").hasAuthority("sys:user")
//			.antMatchers(HttpMethod.GET, "/role").hasAuthority("sys:role")
//			.antMatchers("/permission").access("hasAuthority('sys:permission') or hasRole('ADMIN','ROOT')")
//		
//		;
	}

}
