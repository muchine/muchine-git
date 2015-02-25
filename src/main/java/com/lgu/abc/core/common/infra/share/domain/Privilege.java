package com.lgu.abc.core.common.infra.share.domain;

import org.apache.commons.lang.Validate;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

public @Data class Privilege implements Comparable<Privilege> {
	
	@JsonIgnore
	private AccessLevel level;
	
	public Privilege() {
		this.level = AccessLevel.NONE;
	}
	
	public Privilege(AccessLevel level) {
		this.level = level;
	}
	
	public Privilege(boolean read, boolean write, boolean manage) {
		this();
		Assert.isTrue(read || write || manage, "at least one argument should be true.");
		
		setRead(read);
		setWrite(write);
		setManage(manage);
	}
	
	public boolean isRead() {
		return level.canRead();
	}
	
	public boolean isWrite() {
		return level.canWrite();
	}
	
	public boolean isManage() {
		return level.canManage();
	}
		
	public void setRead(boolean read) {
		if (read && !isRead()) level = AccessLevel.READ;
	}
	
	public void setWrite(boolean write) {
		if (write && !isWrite()) level = AccessLevel.WRITE;
	}
	
	public void setManage(boolean manage) {
		if (manage && !isManage()) level = AccessLevel.MANAGE;
	}

	@Override
	public int compareTo(Privilege o) {
		Validate.notNull(o, "compared object is null.");
		return this.level.compareTo(o.level);
	}
			
}
