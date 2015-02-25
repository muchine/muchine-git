package com.lgu.abc.core.prototype.workspace.base.count;

import lombok.Data;

import com.lgu.abc.core.common.interfaces.Identifiable;

public @Data class WorkspaceCount implements Identifiable {

	private String userId;
	
	private int count;
	
	public WorkspaceCount() {}
	
	public WorkspaceCount(String userId, int count) {
		this.userId = userId;
		this.count = count;
	}
	
	public void add(int increment) {
		this.count += increment;
	}

	@Override
	public String getId() {
		return userId;
	}

	@Override
	public boolean identical(Identifiable object) {
		if (!(object instanceof WorkspaceCount)) return false;
		
		return this.userId.equals(object.getId());
	}
	
}
