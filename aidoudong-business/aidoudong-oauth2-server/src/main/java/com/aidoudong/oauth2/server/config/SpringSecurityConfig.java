package com.aidoudong.oauth2.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.aidoudong.oauth2.server.service.CustomUserDetailsService;
import com.aidoudong.oauth2.server.session.CustomLogoutHandler;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	/**
	 * 退出清除缓存
	 */
	@Autowired
	private CustomLogoutHandler customLogoutHandler;
	
	/**
	 * 为了解决退出重新登录的问题
	 * @return
	 */
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService)
		;
	}
	
	@Bean //password模式需要此bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin() //表单认证方式
			.and()
			.sessionManagement() //session管理
			.maximumSessions(1) //每个用户在系统中最多可以有多少session
			.and().and()
			.logout()
			.addLogoutHandler(customLogoutHandler) //退出清除缓存
			.logoutUrl("/logout") // 退出请求路径
			.deleteCookies("JSESSIONID") //退出后删除cookie值
			
		;
		
	}
	
}
