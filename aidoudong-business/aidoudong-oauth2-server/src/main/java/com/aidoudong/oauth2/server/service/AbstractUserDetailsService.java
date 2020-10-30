package com.aidoudong.oauth2.server.service;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import com.aidoudong.entity.business.ClientPermission;
import com.aidoudong.entity.business.ClientUser;
import com.aidoudong.service.business.ClientPermissionService;

public abstract class AbstractUserDetailsService implements UserDetailsService{
	
	@Autowired
	private ClientPermissionService clientPermissionService;
	
	/**
	 * 这个方法交给子类去实现，查询用户信息
	 * @param usernameOrMobile 用户名/手机
	 * @return
	 */
	public abstract ClientUser findClientUser(String usernameOrMobile);
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
		// 1，通过请求的用户名去数据库中查询用户信息
		ClientUser clientUser = findClientUser(usernameOrMobile);
		// 通过用户id去获取权限信息
		findClientPermission(clientUser);
		return clientUser;
	}

	private void findClientPermission(ClientUser clientUser) {
		if(clientUser == null) {
			throw new UsernameNotFoundException("用户名或密码错误");
		}
		
		// 2，查询该用户有哪些权限
		List<ClientPermission> clientPermissionList = clientPermissionService.findByUserId(clientUser.getId());
		
		if(CollectionUtils.isEmpty(clientPermissionList)) {
			return;
		}
		
		// 在左侧菜单动态渲染会使用，目前先把它传入
		clientUser.setPermissions(clientPermissionList);
		
		// 3，封装用户信息和权限信息
		List<GrantedAuthority> authorities = Lists.newArrayList();
		for(ClientPermission permission : clientPermissionList) {
			// 权限标识
			String code = permission.getCode();
			authorities.add(new SimpleGrantedAuthority(code));
		}
		clientUser.setAuthorities(authorities);
		
		// 4，交给springsecurity自动进行身份认证 
	}
	
}
