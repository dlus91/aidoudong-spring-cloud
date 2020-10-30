package com.aidoudong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 当前类用于管理所有的资源：认证服务器资源，商品资源服务器
 */
@Configuration
public class ResourceServerConfig {
	
	public static final String RESOURCE_ID = "product-server";
	@Autowired
	private TokenStore tokenStore;
	
	/**
	 * 该内部类 认证服务器的资源
	 */
	@Configuration
	@EnableResourceServer
	public class AuthResourceServerConfig extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			// 当前资源服务器的资源id，认证服务器会认证客户端有没有权限访问这个资源id的权限 ，有则可以访问当前服务
			resources
				.resourceId(RESOURCE_ID)
				.tokenStore(tokenStore)
			;
		}
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.anyRequest().permitAll()
				
			;
		}
	}
	
	
	/**
	 * 该内部类 资源服务器的资源
	 */
	@Configuration
	@EnableResourceServer
	public class ApiResourceServerConfig extends ResourceServerConfigurerAdapter {
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			// 当前资源服务器的资源id，认证服务器会认证客户端有没有权限访问这个资源id的权限 ，有则可以访问当前服务
			resources
				.resourceId(RESOURCE_ID)
				.tokenStore(tokenStore)
			;
		}
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/api/**")
				.access("#oauth2.hasScope('api')")
				
			;
		}
	}
	
}
