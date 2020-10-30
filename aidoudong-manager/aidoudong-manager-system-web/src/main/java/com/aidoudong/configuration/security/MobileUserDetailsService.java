package com.aidoudong.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aidoudong.entity.system.SysUser;
import com.aidoudong.service.system.SysUserService;

/**
 * 通过手机号获取用户信息和权限资源
 */
@Component("mobileUserDetailsService") //要注入到容器中
public class MobileUserDetailsService extends AbstractUserDetailsService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	SysUserService sysUserService;

	@Override
	public SysUser findSysUser(String usernameOrMobile) {
		logger.info("请求的手机号是："+usernameOrMobile);		
		
		// 1，通过请求的用户名去数据库中查询用户信息
		return sysUserService.findByMobile(usernameOrMobile);
	}

}
