package com.aidoudong.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 加载认证信息配置类
 * @author ldd
 */
@Configuration
public class ReloadMessageConfig {

	/**
	 * 加载中文的认证提示信息
	 * @return
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource =  new ReloadableResourceBundleMessageSource();
		//路径不要加后缀 
		//该文件允许自己复制出来做修改,只需要修改路径即可
//		messageSource.setBasename("classpath:org/springframework/security/messages_zh_cn");
		messageSource.setBasename("classpath:language/messages_zh_cn");
		return messageSource;
	}
	
}
