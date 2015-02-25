package com.lgu.abc.core.base.domain.query;

import java.util.List;

import javax.validation.constraints.Null;

import lombok.Data;

public @Data class ResolverCondition {

	@Null
	private String companyId;
	
	@Null
	private List<String> workspaces;
	
	@Null
	private List<String> shared;
	
	public void clear() {
		companyId = null;
		workspaces = null;
		shared = null;
	}
	
}
