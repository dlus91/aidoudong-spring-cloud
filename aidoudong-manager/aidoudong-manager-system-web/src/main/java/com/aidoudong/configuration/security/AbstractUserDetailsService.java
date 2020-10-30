package com.aidoudong.configuration.security;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.aidoudong.entity.system.SysPermission;
import com.aidoudong.entity.system.SysUser;
import com.aidoudong.service.system.SysPermissionService;

public abstract class AbstractUserDetailsService implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SysPermissionService sysPermissionService;
	
	/**
	 * 这个方法交给子类去实现，查询用户信息
	 * @param usernameOrMobile 用户名/手机
	 * @return
	 */
	public abstract SysUser findSysUser(String usernameOrMobile);
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
		// 1，通过请求的用户名去数据库中查询用户信息
		SysUser sysUser = findSysUser(usernameOrMobile);
		// 通过用户id去获取权限信息
		findSysPermission(sysUser);
		return sysUser;
	}

	private void findSysPermission(SysUser sysUser) {
		if(sysUser == null) {
			throw new UsernameNotFoundException("用户名或密码错误");
		}
		
		// 2，查询该用户有哪些权限
		List<SysPermission> sysPermissionList = sysPermissionService.findByUserId(sysUser.getId());
		
		if(CollectionUtils.isEmpty(sysPermissionList)) {
			return;
		}
		
		// 在左侧菜单动态渲染会使用，目前先把它传入
		sysUser.setPermissions(sysPermissionList);
		
		// 3，封装用户信息和权限信息
		List<GrantedAuthority> authorities = Lists.newArrayList();
		for(SysPermission permission : sysPermissionList) {
			// 权限标识
			String code = permission.getCode();
			authorities.add(new SimpleGrantedAuthority(code));
		}
		sysUser.setAuthorities(authorities);
		
		// 4，设置权限
		loadMenuTree(sysUser);
		
		logger.info("用户信息："+sysUser.toString());
		
		// 5，交给springsecurity自动进行身份认证 
	}
	
	// 3，封装用户信息和权限信息
	public void loadMenuTree(SysUser sysUser) {
		// 获取到了当前登录用户的菜单和按钮 
		List<SysPermission> permissions = sysUser.getPermissions();
		if(CollectionUtils.isEmpty(permissions)) {
			return;
		}
		
		// 1，获取权限中所有菜单，不需要按钮
		List<SysPermission> menuList = permissions.stream()
			.filter(sysPermission -> sysPermission.getType().equals(1))
			.collect(Collectors.toList());
		
		// 2，获取一下每个菜单的子菜单
		for(SysPermission menu : menuList) {
			// 存放当前菜单的所有子菜单
			List<SysPermission> childMenu = Lists.newArrayList();
			List<String> childUrl = Lists.newArrayList();
			// 获取menu菜单下的所有子菜单
			for(SysPermission p : menuList) {
				// 如果p.parentId等于menu.id则就是它的子菜单
				if(p.getParentId().equals(menu.getId())) {
					childMenu.add(p);
					childUrl.add(p.getUrl());
				}
			}
			// 设置子菜单
			menu.setChildren(childMenu);
			menu.setChildrenUrl(childUrl);
		}
		
		// 3，menuList里面每个SysPermission都有一个子菜单Children集合  
		// 首页Children没有元素，系统管理Children有3个元素（用户，角色，权限）
		List<SysPermission> parentMenuList = menuList.stream()
			.filter(menu -> menu.getParentId().equals(0L))
			.collect(Collectors.toList());
		
		// 最终把获取的跟菜单和子菜单集合重新设置的到permission集合中 
		sysUser.setPermissions(parentMenuList);
		
	}
	
}
