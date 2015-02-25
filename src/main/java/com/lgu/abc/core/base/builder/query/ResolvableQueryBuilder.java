package com.lgu.abc.core.base.builder.query;

import com.lgu.abc.core.base.domain.query.ResolvableQuery;

public abstract class ResolvableQueryBuilder<Q extends ResolvableQuery, B> extends AbstractQueryBuilder<Q, B> {

	protected final Q query;
	
	protected ResolvableQueryBuilder(Q query) {
		this.query = query;
	}
	
	public B company(String companyId) {
		query.findByCompanyId(companyId);
		return builder();
	}
	
	public B workspace(String workspaceId) {
		query.findByWorkspaceId(workspaceId);
		return builder();
	}
	
}
