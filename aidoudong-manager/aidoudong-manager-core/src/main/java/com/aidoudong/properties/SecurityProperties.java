package com.aidoudong.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aidoudong.security")
public class SecurityProperties {
	
	//会将 aidoudong.security.authentication 下面的值绑定到AuthenticationProperties对象里面
	private Authentication authentication;

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
}
