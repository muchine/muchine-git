package com.lgu.abc.core.common.domain.security;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.validation.IpAddress;
import com.lgu.abc.core.support.annotation.Domain;

@Domain
public @Data class IpAccess implements Identifiable {
	
	private static final String WILDCARD = "*";
	
	private String id;
	
	@NotNull
	private boolean canAccess = true;
	
	@IpAddress 
	private String ip;
	
	@Length(max=1000)
	private String description;
	
	public IpAccess() {}
	
	public IpAccess(String ip) {
		this.ip = ip;
	}

	@Override
	public boolean identical(Identifiable object) {
		return object != null && this.id.equals(object.getId());
	}
	
	public boolean allows(String ip) {
		if (StringUtils.isEmpty(ip)) return false;
		return canAccess && matches(ip);
	}
	
	private boolean matches(String ip) {
		String[] tokenized = this.ip.split("\\.");
		String[] compared = ip.split("\\.");
		
		for (int i = 0; i < tokenized.length; i++) {
			if (!WILDCARD.equals(tokenized[i]) && !tokenized[i].equals(compared[i])) return false;
		}
		
		return true;
	}
	
}
