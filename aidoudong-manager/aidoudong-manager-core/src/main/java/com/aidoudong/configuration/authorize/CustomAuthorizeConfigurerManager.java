package com.aidoudong.configuration.authorize;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 将所有的授权配置统一的管理起来
 */
@Component
public class CustomAuthorizeConfigurerManager implements AuthorizeConfigurerManager {
	
	@Autowired
	List<AuthorizeConfigurerProvider> authorizeConfigurerProviders;
	
	/**
	 * 将一个个AuthorizeConfigurerProvider的实现类，传入配置的参数ExpressionInterceptUrlRegistry
	 */
	@Override
	public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		for(AuthorizeConfigurerProvider provider : authorizeConfigurerProviders) {
			provider.configure(config);
		}
	}
	

}
