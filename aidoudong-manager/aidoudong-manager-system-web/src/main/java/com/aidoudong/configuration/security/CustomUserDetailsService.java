package com.aidoudong.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aidoudong.entity.system.SysUser;
import com.aidoudong.service.system.SysUserService;

/**
 * 通过账号获取用户信息和权限资源
 */
@Component("customUserDetailsService")
public class CustomUserDetailsService extends AbstractUserDetailsService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired //不能删除不然SessionRegistry会报错循环引用的错误
	PasswordEncoder passwordEncoder;
	@Autowired
	SysUserService sysUserService;

	@Override
	public SysUser findSysUser(String usernameOrMobile) {
		logger.info("请求认证的用户名" + usernameOrMobile);
		
		// 1，通过请求的用户名去数据库中查询用户信息
		return sysUserService.findByUsername(usernameOrMobile);
	}
	
}
