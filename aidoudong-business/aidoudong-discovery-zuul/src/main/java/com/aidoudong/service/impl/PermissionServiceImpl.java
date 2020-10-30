package com.aidoudong.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.aidoudong.service.PermissionService;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@SuppressWarnings("unchecked")
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();
        String requestUrl = request.getRequestURI();
        logger.info("requestUrl:{}",requestUrl);
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if(principal != null){
            if(CollectionUtils.isEmpty(grantedAuthorityList)){
                return hasPermission;
            }
            for(SimpleGrantedAuthority authority:grantedAuthorityList){
                if (antPathMatcher.match(authority.getAuthority(),requestUrl)){
                    hasPermission = true;
                    break;
                }
            }
        }
        
//        return hasPermission;
        return true;
	}
	
}
