package com.aidoudong.configuration.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import com.aidoudong.configuration.authentication.CustomAuthenticationFailureHandler;
import com.aidoudong.configuration.authentication.CustomAuthenticationSuccessHandler;

/**
 * 用户组合其他关于手机登录的组件
 */
@Component
public class MobileAuthenticationConfig 
	extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	@Autowired
	CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	@Autowired
	UserDetailsService mobileUserDetailsService;
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		MobileAuthenticationFilter authenticationFilter = new MobileAuthenticationFilter();
		// 获取容器中已经存在的AuthenticationManager对象, 并传入到authenticationFilter里面
		authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		
		// 指定记住我的功能
		authenticationFilter.setRememberMeServices(http.getSharedObject(RememberMeServices.class));
		
		// 关于session重复登录管理
		authenticationFilter.setSessionAuthenticationStrategy(http.getSharedObject(SessionAuthenticationStrategy.class));
		
		authenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
		authenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
		
		// 构建一个MobileAuthenticationProvider实例,  接收mobileUserDetailsService通过手机查询用户的信息
		MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
		provider.setUserDetailsService(mobileUserDetailsService);
		
		// 将provider绑定到HttpSecurity上, 并将手机号认证过滤器绑定到用户名密码认证过滤器之后
		http.authenticationProvider(provider)
			.addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
}
