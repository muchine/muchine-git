package com.lgu.abc.core.web.session.cache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.plugin.session.destroy.GlobalSessionFinalizer;
import com.lgu.abc.core.plugin.session.destroy.SessionFinalizable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.repo.UserRegistry;

@Component
public class CacheableSessionManager implements SessionFinalizable {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String CACHE_NAME = "sessions";
	
	private static final Set<CacheableSessionInitializable> initializables = new HashSet<CacheableSessionInitializable>();

	@Autowired
	private CacheManager cacheManager;
	
	private CacheableSessionManager() {
		GlobalSessionFinalizer.add(this);
	}
	
	public synchronized void add(CacheableSessionInitializable initializable) {
		initializables.add(initializable);
	}
	
	public void createSession(String sessionId, User user) {
		CacheableSession session = createCacheableSession(user);
		storeSessionInCache(sessionId, session);
	}
	
	public User getUserSession(String sessionId) {
		String userId = getUserId(sessionId);
		return StringUtils.isEmpty(userId) ? null : UserRegistry.get(userId);
	}
	
	public String getSessionAttribute(String sessionId, String key) {
		CacheableSession session = getSession(sessionId);
		return session == null ? null : session.get(key);
	}
	
	public void setSessionAttribute(String sessionId, String key, String value) {
		CacheableSession session = getSession(sessionId);
		if (session == null) return;
		
		session.put(key, value);
	}
	
	@Override
	public void finalize(HttpSession session) {
		Cache cache = cacheManager.getCache(CACHE_NAME);
		cache.evict(session.getId());
	}
	
	private CacheableSession getSession(String sessionId) {
		Cache cache = cacheManager.getCache(CACHE_NAME);
		ValueWrapper wrapper = cache.get(sessionId);
		
		if (wrapper == null || wrapper.get() == null) return null;
		return (CacheableSession) wrapper.get();
	}
	
	private void storeSessionInCache(String sessionId, CacheableSession session) {
		Cache cache = cacheManager.getCache(CACHE_NAME);
		logger.debug("putting session into cache... id: " + sessionId + ", session: " + session);
		cache.put(sessionId, session);
	}
	
	private CacheableSession createCacheableSession(User user) {
		CacheableSession session = new CacheableSession(user.getId());
		for (CacheableSessionInitializable initializable : initializables) {
			Map<String, String> attributes = initializable.initialize(user);
			addSessionAttributes(session, attributes);
		}
			
		return session;
	}
	
	private void addSessionAttributes(CacheableSession session, Map<String, String> attributes) {
		for (String key : attributes.keySet()) {
			session.create(key, attributes.get(key));
		}
	}
	
	private String getUserId(String sessionId) {
		CacheableSession session = getSession(sessionId);
		return session == null ? null : session.getUserId();
	}
	
}
