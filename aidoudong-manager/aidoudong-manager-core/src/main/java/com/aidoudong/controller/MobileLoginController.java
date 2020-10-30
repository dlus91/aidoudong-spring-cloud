package com.aidoudong.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aidoudong.properties.SimpleResultView;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aidoudong.configuration.authentication.mobile.SmsSend;

/**
 * 关于手机登录登录层
 */
@Controller
public class MobileLoginController {
	
	public static final String SESSION_KEY = "SESSION_KEY_MOBILE_CODE";
	@Autowired
	private SmsSend smsSend;
	
	/**
	 * 前往手机验证码登陆页
	 */
	@GetMapping("/mobile/page")
	public String toMobilePage() {
		return "login-mobile"; //templates: login-mobile.html
	}
	
	@GetMapping("/code/mobile")
	@ResponseBody
	public String smsCode(HttpServletRequest request,HttpServletResponse response) {
		// 1, 生成一个手机验证码
		String code = RandomStringUtils.randomNumeric(4);
		// 2, 将手机验证码保存到session中
		request.getSession().setAttribute(SESSION_KEY, code);
		// 3, 发送验证码到用户手机上
		smsSend.sendSms(request.getParameter("mobile"), code);
		return new SimpleResultView().ok("成功");
	}
	
}
