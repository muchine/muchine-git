package com.lgu.abc.core.common.vo.id;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ToString(of="pattern") @EqualsAndHashCode(of="pattern")
public class ResourceID implements Comparable<ResourceID> {
	
	private static final String DELIMITER = "/";
	
	private static final String WILDCARD = "*";
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String pattern;
	
	public ResourceID(Class<?> type, String id) {
		this(type.getSimpleName(), id);
	}
	
	public ResourceID(String type, String id) {
		this.pattern = type + DELIMITER + id;
	}
	
	/*
	 * NOTE Implement resource hierarchy later.
	 */
	public ResourceID(Class<?> type) {
		this(type.getSimpleName());
	}
	
	public ResourceID(String type) {
		this.pattern = type + DELIMITER + WILDCARD;
	}
	
	public ResourceID concatenate(ResourceID resourceId) {
		this.pattern += DELIMITER + resourceId.pattern;
		return this;
	}
	
	public boolean matches(ResourceID resource) {
		/*
		 * 1. Identical: Company/18 is matched with Company/18
		 * 2. Wildcard:  Company/18 is matched with Company/*
		 * 3. Contains:  Company/18 is matched with Company/18/Calendar/19, but not vice versa
		 */
		String[] tokens = pattern.split(DELIMITER);
		String[] matched = resource.pattern.split(DELIMITER);
		
		if (tokens.length > matched.length) return false;
		
		for (int i = 0; i < tokens.length; i++) {
			if (!hasWildcard(tokens[i], matched[i]) && !tokens[i].equals(matched[i])) return false;
		}
		
		return true;
	}
	
	private boolean hasWildcard(String token, String compared) {
		return hasWildcard(token) || hasWildcard(compared);
	}
	
	private boolean hasWildcard(String token) {
		return WILDCARD.equals(token);
	}

	@Override
	public int compareTo(ResourceID o) {
		Validate.notNull(o, "resource id to be compared is null.");
		if (this.pattern.equals(o.pattern)) return 0;
		
		// More precise and longer one should be first
		if (o.pattern.length() != pattern.length()) return o.pattern.length() - pattern.length();
		
		if (!o.pattern.endsWith(DELIMITER) && pattern.endsWith(DELIMITER)) return 1;
		if (o.pattern.endsWith(DELIMITER) && !pattern.endsWith(DELIMITER)) return -1;
		
		return 0;
	}
	
}
