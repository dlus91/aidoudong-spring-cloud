package com.aidoudong.oauth2.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

@Component
public class TokenConfig {
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		// 非对称加密
		// 第一个参数是密钥证书文件，第二参数密钥库口令
		// 使用私钥进行签名
		KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
				new ClassPathResource("aidoudong-jwt.jks")
				, "aidoudong123".toCharArray());
		converter.setKeyPair(factory.getKeyPair("aidoudong-jwt"));
		return converter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		// JWT 管理令牌
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	
}
