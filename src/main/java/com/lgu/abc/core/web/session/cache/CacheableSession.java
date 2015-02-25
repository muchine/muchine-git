package com.lgu.abc.core.web.session.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

import com.lgu.abc.core.web.session.cache.exception.DuplicateCacheableSessionAttributeException;

@SuppressWarnings("serial")
public @Data class CacheableSession implements Serializable {

	private final String userId;
	
	private final Map<String, String> content = new ConcurrentHashMap<String, String>();

	public CacheableSession(String userId) {
		this.userId = userId;
	}
	
	void create(String key, String value) {
		if (content.get(key) != null)
			throw new DuplicateCacheableSessionAttributeException();
		
		put(key, value);
	}
	
	void put(String key, String value) {
		content.put(key, value);
	}
	
	String get(String key) {
		return content.get(key);
	}
	
}
