package com.aidoudong.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aidoudong.configuration.authentication.mobile.SmsSend;

@Component
public class MobileSmsCodeSender implements SmsSend {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean sendSms(String mobile, String content) {
		logger.info("web应用新的短信 验证码 接口---向手机号："+mobile+"发送的短信为："+content);
		return false;
	}

}
