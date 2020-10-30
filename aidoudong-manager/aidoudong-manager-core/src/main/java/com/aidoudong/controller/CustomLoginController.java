package com.aidoudong.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
public class CustomLoginController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	@Autowired
	private DefaultKaptcha defaultKaptcha;

	@GetMapping("/login/page")
	public String toLogin() {
		return "login"; //classpath: /templates/login.html
	}
	
	@GetMapping("/code/image")
	public void imageCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 1，获取验证码字符串
		String code = defaultKaptcha.createText();
		logger.info("验证码："+code);
		// 2，字符串把 它放到session中
		request.getSession().setAttribute(SESSION_KEY, code);
		// 3，获取验证码图片
		BufferedImage image = defaultKaptcha.createImage(code);
		// 4，将验证码图片把它写出去
		ServletOutputStream output = response.getOutputStream();
		ImageIO.write(image, "jpg", output);
	}
	
}
