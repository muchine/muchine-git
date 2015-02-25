package com.lgu.abc.core.base.domain.query;

import java.util.List;

import javax.validation.constraints.Null;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.mybatis.handlers.query.interfaces.Queryable;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data abstract class ResolvableQuery extends AuthorizableQuery {
	
	@Null
	private ResolverCondition resolver = new ResolverCondition();
	
	private String workspaceId;
	
	public void findByCompanyId(String companyId) {
		clear();
		setCompanyId(companyId);
	}
	
	public void findByWorkspaceId(String workspaceId) {
		clear();
		setWorkspaceId(workspaceId);
	}
	
	@Override
	public void clear() {
		super.clear();
		
		resolver = null;
		workspaceId = null;
	}
		
	public final boolean isResolving() {
		return queryTypes() != null && resolver != null;
	}
	
	@Override
	protected void findWithQueryable(Queryable queryable) {
		super.findWithQueryable(queryable);
		resolver = new ResolverCondition();
	}
	
	public void findByIds(List<String> ids) {
		Validate.noNullElements(ids, "ids has null element.");
		Validate.notEmpty(ids, "ids is empty.");
		
		clear();
		setIds(ids);
	}
	
	public void resolveCompany(String companyId) {
		if (resolver == null) resolver = new ResolverCondition();
		resolver.setCompanyId(companyId);
	}
		
	public void resolveWorkspaces(List<String> workspaces) {
		if (resolver == null) resolver = new ResolverCondition();
		resolver.setWorkspaces(workspaces);
	}
	
	public void resolveAuthorities(List<String> shared) {
		if (resolver == null) resolver = new ResolverCondition();
		resolver.setShared(shared);
	}
	
	public void clearResolver() {
		if (resolver != null) resolver.clear();
	}
	
}
