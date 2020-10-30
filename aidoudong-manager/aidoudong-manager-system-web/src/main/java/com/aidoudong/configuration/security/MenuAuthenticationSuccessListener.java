package com.aidoudong.configuration.security;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.aidoudong.configuration.authentication.AuthenticationSuccessListener;
import com.aidoudong.entity.system.SysPermission;
import com.aidoudong.entity.system.SysUser;

/**
 * 当认证成功后，会触发此实现类方法 successListener
 */
//@Component
public class MenuAuthenticationSuccessListener implements AuthenticationSuccessListener {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 查询用户所拥有的权限菜单
	 * @param request
	 * @Param response
	 * @Param authentication 当用户认证通过后，会将认证对象传入 
	 */
	@Override
	public void successListener(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logger.info("================查询用户所拥有的权限菜单==============");
		Object principal =  authentication.getPrincipal();
		if(principal != null && principal instanceof SysUser) {
			SysUser sysUser = (SysUser)principal;
			loadMenuTree(sysUser);
			// #authentication.principal.permissions
			logger.info("SysUser:"+sysUser.toString());
		}
	}
	
	/**
	 * 只加载菜单，不需要加载按钮
	 * @param sysUser
	 */
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
