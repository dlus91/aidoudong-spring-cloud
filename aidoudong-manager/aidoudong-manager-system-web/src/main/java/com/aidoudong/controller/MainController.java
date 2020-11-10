package com.aidoudong.controller;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultViewBuilder;
import com.aidoudong.common.utils.DictionaryCodeUtil;
import com.aidoudong.entity.system.SysUser;
import com.aidoudong.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private BaseResultView fastJsonResultView;
	@Autowired
	private DictionaryCodeUtil dictionaryCodeUtil;
	
	@GetMapping({"hello","say"})
	@ResponseBody
	public String hello(HttpServletRequest request) {
		logger.info("hello:"+request.getSession().getId());
		logger.info("client ipaddress："+request.getRemoteAddr());
		List<SysUser> list = sysUserService.list();
		logger.info("list : " + list);
		return String.format("%s hello %s", "dd","haha");
	}
	
	//=========================获取用户信息的三种方式===========================
	@GetMapping({"index","/",""})
	public String index(Map<String, Object> map) {
//		// 第一种方式：
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		Object principal = authentication.getPrincipal();
//		// principal 认证后是账号, 认证后是用户信息 
//		if(principal != null && principal instanceof UserDetails) {
//			UserDetails userDetails = (UserDetails) principal;
//			map.put("username", userDetails.getUsername());
//		}
		
		return "index";//resources/templates/index.html	
	}
	
	// 第二种方式：Authentication会自动注入
	@GetMapping("/user/info")
	@ResponseBody
	public Object userInfo(Authentication authentication) {
		return authentication.getPrincipal();
	}
	
	// 第二种方式：@AuthenticationPrincipal会自动注入对应的UserDetails
	@GetMapping("/user/info2")
	@ResponseBody
	public Object userInfo2(@AuthenticationPrincipal UserDetails userDetails) {
		return userDetails;
	}
	
	@GetMapping("/refresh/dectionary")
	@ResponseBody
	public String refreshDectionaryTable() {
		dictionaryCodeUtil.refresh();
		return fastJsonResultView.ok(ResultViewBuilder.success());
	}
	
}
