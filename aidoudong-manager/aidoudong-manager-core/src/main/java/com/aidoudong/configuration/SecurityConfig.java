package com.aidoudong.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.aidoudong.configuration.authentication.ImageCodeValidateFilter;
import com.aidoudong.configuration.authentication.mobile.MobileAuthenticationConfig;
import com.aidoudong.configuration.authentication.mobile.MobileVaidateFilter;
import com.aidoudong.configuration.authentication.session.CustomLogoutHandler;
import com.aidoudong.configuration.authorize.AuthorizeConfigurerManager;
import com.aidoudong.properties.Authentication;
import com.aidoudong.properties.SecurityProperties;

@Configuration
@EnableWebSecurity //开启springsecurity过滤器链
/**
 * 开启注释方法级别权限控制
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private UserDetailsService customUserDetailsService;
	@Autowired
	private AuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler customAuthenticationFailureHandler;
	@Autowired
	private ImageCodeValidateFilter imageCodeValidateFilter;
	@Autowired
	private DataSource systemDataSource;
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	/**
	 * 当同个用户session数量超过指定值之后,会调用实现类
	 */
	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
	/**
	 * 校验验证码
	 */
	@Autowired
	private MobileVaidateFilter mobileVaidateFilter;
	/**
	 * 校验手机是否存在
	 */
	@Autowired
	private MobileAuthenticationConfig mobileAuthenticationConfig;
	/**
	 * 退出清除缓存
	 */
	@Autowired
	private CustomLogoutHandler customLogoutHandler;
	
	@Autowired
	private AuthorizeConfigurerManager customAuthorizeConfigurerManager;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//明文+随机盐值，加密存储
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 记住我的功能
	 * @return
	 */
	@Bean
	public JdbcTokenRepositoryImpl jdbcTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(systemDataSource);
		// 是否在项目启动时自动创建表，true自动创建
		jdbcTokenRepository.setCreateTableOnStartup(false);
		return jdbcTokenRepository;
	}
	
	/**
	 * 认证管理器
	 * 1，认证信息（用户名/密码）
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//数据库存储的密码必须是加密后的，不然就报错：There is no PasswordEncoder mapped for the id "null"
//		auth.inMemoryAuthentication()
//			.withUser("admin")
//			.password(passwordEncoder().encode("123456"))
//			.authorities("ADMIN")
		auth.userDetailsService(customUserDetailsService)
		;
	}
	
	/**
	 * 认证成功后，会重定向到上一次请求上
	 * 资源权限配置
	 * 1，被拦截的资源
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic() //采用 httpBasic认证方式
		Authentication authenticationProperties = securityProperties.getAuthentication();
		http
			.addFilterBefore(mobileVaidateFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
			.formLogin() //表单认证方式
			.loginPage(authenticationProperties.getLoginPage())

			.loginProcessingUrl(authenticationProperties.getLoginProcessingUrl()) //登录表单提交处理url，默认是/login
			.usernameParameter(authenticationProperties.getUsernameParameter()) //默认是username
			.passwordParameter(authenticationProperties.getPasswordParameter()) //默认是password
			.successHandler(customAuthenticationSuccessHandler)
			.failureHandler(customAuthenticationFailureHandler)
			.and()
			.rememberMe() //记住我功能配置
			.tokenRepository(jdbcTokenRepository()) //保存登录信息
			.tokenValiditySeconds(authenticationProperties.getTokenValiditySeconds()) //记住我有效时长
			.and()
			.sessionManagement() //session管理
			.invalidSessionStrategy(invalidSessionStrategy) //当session失效的处理类
			.maximumSessions(1) //每个用户在系统中最多可以有多少session
			.expiredSessionStrategy(sessionInformationExpiredStrategy) //超过最大数执行这个实现类
//			.maxSessionsPreventsLogin(true) //当一个用户达到最大session数,则不允许后面再登录
			.sessionRegistry(sessionRegistry())
			.and().and()
			.logout()
			.addLogoutHandler(customLogoutHandler) //退出清除缓存
			.logoutUrl("/user/logout") // 退出请求路径
			.logoutSuccessUrl("/login/page") //退出成功后跳转地址
			.deleteCookies("JSESSIONID") //退出后删除cookie值
		;
		
		http.csrf().disable(); //关闭跨站请求伪造
		// 将手机认证添加到过滤器链上
		http.apply(mobileAuthenticationConfig);
		
		// 将所有的授权配置统一的管理起来
		customAuthorizeConfigurerManager.configure(http.authorizeRequests());
	}
	
	/**
	 * 为了解决退出重新登录的问题
	 * @return
	 */
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	

	/**
	 * 一般是针对静态资源放行
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
//		web.ignoring().antMatchers("/**");
	}

}
