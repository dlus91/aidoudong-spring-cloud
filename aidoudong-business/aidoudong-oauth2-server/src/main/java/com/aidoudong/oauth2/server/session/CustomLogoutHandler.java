package com.aidoudong.oauth2.server.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler{
	
	@Autowired
	private SessionRegistry sessionRegistry;

	@Override
	public void logout(HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication) {
		// 退出之后，将对应session从缓存中清除 SessionRegistryImpl.principals
		sessionRegistry.removeSessionInformation(request.getSession().getId());
		
//		System.out.println("====authentication.getAuthorities().size():"+authentication.getAuthorities().size());
	}

}
