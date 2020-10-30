package com.aidoudong;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplicationApp {
	
	@EnableWebSecurity
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter{
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			super.configure(http);
			http.csrf().disable();
		}
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaApplicationApp.class).web(WebApplicationType.SERVLET).run(args);
		System.out.println("==========start===========");
	}
	
	
	
}
