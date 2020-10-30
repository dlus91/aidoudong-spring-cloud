package com.aidoudong.oauth2.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityBean {

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
				
	}
	
}
