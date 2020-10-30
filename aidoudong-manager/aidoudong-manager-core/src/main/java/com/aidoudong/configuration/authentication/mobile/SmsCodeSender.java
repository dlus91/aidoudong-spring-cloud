package com.aidoudong.configuration.authentication.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送短信验证码，第三方的短信服务接口
 */
public class SmsCodeSender implements SmsSend {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @param mobile 手机号
	 * @Param content 发送的内容：接受的是验证码
	 */
	@Override
	public boolean sendSms(String mobile, String content) {
		String sendContent = String.format("aidoudong感谢您的使用,验证码：%s,请勿泄露给别人.", content);
		//调用第三方发送 功能的sdk
		logger.info("向手机号："+mobile+"发送的短信为："+sendContent);
		
		return true;
	}

}
