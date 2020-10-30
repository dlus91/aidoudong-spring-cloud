package com.aidoudong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 资源服务器相关配置类
 */
@Configuration
@EnableResourceServer //标识为资源服务器，请求服务中的资源就要带着token过来，如果找不到token或者token无效，则访问不了资源
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法级别权限控制
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	public static final String RESOURCE_ID = "product-server";
	
	@Autowired
	private TokenStore tokenStore;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// 当前资源服务器的资源id，认证服务器会认证客户端有没有权限访问这个资源id的权限 ，有则可以访问当前服务
		resources
			.resourceId(RESOURCE_ID)
			.tokenStore(tokenStore)
//			.tokenServices(tokenServices())
			
		;
	}
	
//	public ResourceServerTokenServices tokenServices() {
//		// 远程认证服务器进行校验token是否有效
//		RemoteTokenServices service  = new RemoteTokenServices();
//		// 请求认证服务器校验的地址，默认情况下这个地址在认证服务器是拒绝访问的，要设置为认证通过后可以访问
//		service.setCheckTokenEndpointUrl("http://localhost:11210/auth/oauth/check_token");
//		service.setClientId("aidoudong-pc");
//		service.setClientSecret("123456");
//		
//		return service;
//	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			// SpringSecurity不会使用也不会创建HttpSession实例 
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			//以下就是权限规则配置
			//所有请求都会先进这个方法，校验后才进入方法，如果这里验证不过则方法级上的注解也是没用的
//			.antMatchers("/product/*").hasAuthority("product")
			//所有请求，都需要有all范围（scope）
			.antMatchers("/**").access("#oauth2.hasAnyScope('all')")
			//等价于上面
//			.anyRequest().access("#oauth2.hasAnyScope('all')")
		;
	}
	
}
