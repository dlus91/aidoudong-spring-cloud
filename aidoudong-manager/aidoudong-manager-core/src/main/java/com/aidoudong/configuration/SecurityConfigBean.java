package com.aidoudong.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.aidoudong.configuration.authentication.mobile.SmsCodeSender;
import com.aidoudong.configuration.authentication.mobile.SmsSend;
import com.aidoudong.configuration.authentication.session.CustomInvalidSessionStrategy;
import com.aidoudong.configuration.authentication.session.CustomSessionInformationExpiredStrategy;

/**
 * 主要为容器中添加bean实例
 */
@Configuration
public class SecurityConfigBean {
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
		return new CustomSessionInformationExpiredStrategy();
	}
	
	/**
	 * 当session失效策略实例
	 */
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy() {
		return new CustomInvalidSessionStrategy(sessionRegistry);
	}

	/**
	 * @ConditionalOnMissingBean(SmsSend.class)
	 * 默认情况下，采用的是SmsCodeSender实例，
	 * 但是如果容器当中有其他的SmsCodeSender类型的实例，
	 * 那么当前这个SmsCodeSender就失效了
	 */
	@Bean
	@ConditionalOnMissingBean(SmsSend.class)
	public SmsSend smsSend() {
		return new SmsCodeSender();
	}
	
}
