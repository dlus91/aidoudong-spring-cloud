package com.aidoudong.oauth2.server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.aidoudong.oauth2.server.service.CustomUserDetailsService;

/**
 * 认证服务器配置类
 */
@Configuration
@EnableAuthorizationServer //开启了认证服务器
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@SuppressWarnings("unused")
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired //刷新令牌
	private CustomUserDetailsService customUserDetailsService;
	@Autowired //token管理方式，在TokenConfig类中已添加到容器中
	private TokenStore tokenStore;
	@Autowired
	@Qualifier("oauth2DataSource")
	private DataSource dataSource;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Bean
	public ClientDetailsService jdbcClientDetailsService() {
		JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
		return jdbcClientDetailsService;
	}
	
	/**
	 * 配置被允许访问此认证服务器的客户端信息
	 * 1，内存方式
	 * 2，JDBC方式
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 通过jdbc管理客户端信息 建议 在客户端较多情况下   JDBC的优点方便查看修改
		clients
			.withClientDetails(jdbcClientDetailsService())
		;
		
	}
	
	@Bean //授权码管理策略 
	public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
		return new JdbcAuthorizationCodeServices(dataSource);
	}
	
	/**
	 * 关于认证服务器端点配置
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// password 需要这个AuthenticationManager实例
		endpoints.authenticationManager(authenticationManager);
		// 刷新令牌时需要使用
		endpoints.userDetailsService(customUserDetailsService);
		
		// 令牌的管理方式
		endpoints
			.tokenStore(tokenStore)
			.accessTokenConverter(jwtAccessTokenConverter)

		;
		// 授权码管理策略 会把产生的授权码放到 oauth_code表中，如果这个授权码被使用了，则这个授权码会被删除 
		endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());
	}
	
	/**
	 * 令牌端点的安全配置
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 所有人都可访问 /oauth/check_token，默认拒绝访问 
		security.checkTokenAccess("isAuthenticated()");
		// 所有人都可访问 /oauth/token_key 后面要获取公钥，默认拒绝访问
		security.tokenKeyAccess("permitAll()");
		
	}
	
}
