package com.lgu.abc.core.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.plugin.session.init.GlobalSessionInitializer;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.repo.UserRegistry;
import com.lgu.abc.core.web.session.cache.CacheableSessionManager;
import com.lgu.abc.core.web.support.WebUtils;

@Component
public class SessionManager {

	protected static final Log logger = LogFactory.getLog(SessionManager.class);
	
	public static final String USER_SESSION_NAME = "session";
	
	private static CacheableSessionManager sessionManager;
	
	@Autowired
	private GlobalSessionInitializer sessionInitializer;
	
	@Autowired
	private SessionManager(CacheableSessionManager cachableSessionManager) {
		sessionManager = cachableSessionManager;
	}
	
	public void createSession(String attributeName, Object object) {
		HttpServletRequest request = getCurrentRequest();
		
		HttpSession session = request.getSession(true);
		
		AbcConfig configuration = AbcConfig.instance();
		session.setMaxInactiveInterval(configuration.system().session().lifetime());
		session.setAttribute(attributeName, object);
	}
	
	public void createUserSession(User user) {
		createSession(SessionManager.USER_SESSION_NAME, user);
		createSession(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, user.getLocale());
		String sessionId = createSessionId();
		
		sessionManager.createSession(sessionId, user);
			
		sessionInitializer.initialize(user);
	}
	
	public HttpSession getCurrentSession() {
		HttpServletRequest request = getCurrentRequest();
		return request.getSession(false);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getSession(String attributeName, Class<T> type) {
		HttpSession session = getCurrentSession();
		return session == null ? null : (T) session.getAttribute(attributeName);
	}
	
	public static HttpServletRequest getCurrentRequest() {      
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attributes.getRequest();    
	}
	
	public static User getSession(HttpServletRequest request) {
		Validate.notNull(request, "http servlet request is null.");
		
		HttpSession session = request.getSession(false);
		return session == null ? getCachedSession(request) : (User) session.getAttribute(USER_SESSION_NAME);
	}
	
	private static User getCachedSession(HttpServletRequest request) {
		String sessionId = WebUtils.sessionId(request);
		return sessionManager.getUserSession(sessionId);
	}
	
	public User getSession() {
		return getSession(getCurrentRequest());
	}
	
	public void refreshSession() {
		User user = getSession();
		UserRegistry.evict(user.getId());
		
		createUserSession(UserRegistry.get(user.getId()));
	}
	
	public static boolean hasSession(HttpServletRequest request) {
		return getSession(request) != null;
	}
			
	private String createSessionId() {
		String id = getCurrentSession().getId();
		createSession("sessionId", id);
		
		return id;
	}
	
}
