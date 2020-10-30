package com.aidoudong.config;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

@Component
public class TokenConfig {
	
	public static final String PUBLIC_CERT = "public.cert";
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		
		// 非对称加密，资源服务器使用公钥解密
		ClassPathResource resource = new ClassPathResource(PUBLIC_CERT);
		String publicKey = null;
		try {
			publicKey = IOUtils.toString(resource.getInputStream(),"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		converter.setVerifier(new RsaVerifier(publicKey));
		return converter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		// JWT 管理令牌
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	
}
