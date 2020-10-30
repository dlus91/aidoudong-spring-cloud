package com.aidoudong;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableDiscoveryClient
@SpringBootApplication
public class OAuth2ApplicationApp {
	
	/**
	 * cmd先生成aidoudong-jwt.jks和public.cert
	 * aidoudong-jwt.jks 命令：keytool -genkeypair -alias aidoudong-jwt -validity 3650 -keyalg RSA -dname "CN=jwt,OU=cnsesan,O=cnsesan,L=zurich,S=zurich,C=CH" -keypass aidoudong123 -keystore aidoudong-jwt.jks -storepass aidoudong123
	 * public.cert 命令：keytool -list -rfc --keystore aidoudong-jwt.jks | openssl x509 -inform pem -pubkey
	 * 
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(OAuth2ApplicationApp.class).web(WebApplicationType.SERVLET).run(args);
		System.out.println("==========start===========");
	}
	
//	private CorsConfiguration corsConfig() {
//	    CorsConfiguration corsConfiguration = new CorsConfiguration();
//	    // 请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
//	    corsConfiguration.addAllowedOrigin("*");
//	    corsConfiguration.addAllowedHeader("*");
//	    corsConfiguration.addAllowedMethod("*");
//	    corsConfiguration.setAllowCredentials(true);
//	    corsConfiguration.setMaxAge(3600L);
//	    return corsConfiguration;
//	}
//	
//	@Bean
//	public CorsFilter corsFilter() {
//	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    source.registerCorsConfiguration("/**", corsConfig());
//	    return new CorsFilter(source);
//	}
	
	
	
}
