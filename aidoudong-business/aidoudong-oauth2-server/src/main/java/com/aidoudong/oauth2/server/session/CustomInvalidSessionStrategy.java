
package com.aidoudong.oauth2.server.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aidoudong.common.result.ResultView;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * 当session失效后的处理逻辑
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private SessionRegistry sessionRegistry;
	
	public CustomInvalidSessionStrategy(SessionRegistry sessionRegistry) {
		super();
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 因为这里是session超时后的操作，request.getSession是获取当前请求里的session，这里session已经超时就没有了，所以这里会创建一个新的sessionId返回
		// request.getSession(true) 默认是true，表示没有session就创建一个新的。传入false是表示当前没有session就返回一个 null，不会创建一个新的session返回
		logger.info("CustomInvalidSessionStrategy.getSession().getId(): "+request.getSession().getId());
		// request.getRequestedSessionId是获取浏览器中的session，而不是请求中的session，这里超时是后台session超时移除掉，但浏览器cookie还存在，所以这里可以获取到旧的session
		// 这里在下一步才清除浏览器的cookie的
		logger.info("CustomInvalidSessionStrategy.getRequestedSessionId(): "+request.getRequestedSessionId());
		sessionRegistry.removeSessionInformation(request.getRequestedSessionId());
		
		// 要将浏览器中的cookie的jessionid删除
		cancelCookie(request,response);
		
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(JSONObject.toJSONString(ResultView.fail(HttpStatus.UNAUTHORIZED.value(), "登录已超时,请重新登录")));
		
	}
	
	protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("JSESSIONID", null);
		cookie.setMaxAge(0);
		cookie.setPath(getCookiePath(request));
		response.addCookie(cookie);
	}
	
	private String getCookiePath(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return contextPath.length() > 0 ? contextPath : "/";
	}


}
