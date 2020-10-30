package com.aidoudong.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface PermissionService {

	public boolean hasPermission(HttpServletRequest request, Authentication authentication);
	
}
