package com.aidoudong.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OAuth2RestTemplate restTemplate;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/member")
	public String member() {
		restTemplate.getForEntity("http://localhost:9801/api/product/list", String.class);
		ResponseEntity<String> result = restTemplate.getForEntity("http://localhost:9801/api/product/list", String.class);
		JSONObject body = JSONObject.parseObject(result.getBody(),JSONObject.class);
		logger.info("product result:"+body.toString());
		
		return "member";
	}
	
	
	
	
}
