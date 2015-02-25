package com.lgu.abc.core.common.infra.share.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccessLevel implements Comparable<AccessLevel> {

	public static AccessLevel NONE	= new AccessLevel(0, false, false, false);
	public static AccessLevel READ	= new AccessLevel(1, true, false, false);
	public static AccessLevel WRITE	= new AccessLevel(2, true, true, false);
	public static AccessLevel MANAGE	= new AccessLevel(3, true, true, true);

	private final int level;
	
	private final boolean canRead;
	
	private final boolean canWrite;
	
	private final boolean canManage;
	
	private AccessLevel(int level, boolean read, boolean write, boolean manage) {
		this.level = level;
		this.canRead = read;
		this.canWrite = write;
		this.canManage = manage;
	}
	
	public boolean canRead() {
		return canRead;
	}
	
	public boolean canWrite() {
		return canWrite;
	}
	
	public boolean canManage() {
		return canManage;
	}

	@Override
	public int compareTo(AccessLevel o) {
		return this.level - ((AccessLevel) o).level;
	}

	@Override
	public String toString() {
		return "Level(" + level + ")";
	}
	
}
