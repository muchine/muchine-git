package com.lgu.abc.core.prototype.org.user.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.base.builder.query.AbstractQueryBuilder;
import com.lgu.abc.core.base.security.role.company.CompanyRole;
import com.lgu.abc.core.common.query.Searcher;
import com.lgu.abc.core.prototype.org.user.UserQuery;
import com.lgu.abc.core.prototype.org.user.query.UserOrganization;
import com.lgu.abc.core.prototype.org.user.query.UserStatus;

public class UserQueryBuilder extends AbstractQueryBuilder<UserQuery, UserQueryBuilder> {

	private UserQuery query;
	
	public UserQueryBuilder() {
		query = new UserQuery();
	}
	
	public UserQueryBuilder status(UserStatus status) {
		query.setStatus(status);
		return this;
	}
	
	public UserQueryBuilder stopped(boolean stopped) {
		query.setStopped(stopped);
		return this;
	}
	
	public UserQueryBuilder organization(List<String> userIds, List<String> departmentIds) {
		if (CollectionUtils.isEmpty(userIds) && CollectionUtils.isEmpty(departmentIds))	return this;
		
		query.setOrganization(new UserOrganization(userIds, departmentIds));		
		return this;
	}
	
	public UserQueryBuilder company(String companyId) {
		query.setCompanyId(companyId);
		return this;
	}
	
	public UserQueryBuilder department(String departmentId) {
		query.addDepartment(departmentId);
		return this;
	}
	
	public UserQueryBuilder departments(List<String> departmentIds) {
		query.addDepartments(departmentIds);
		return this;
	}
	
	public UserQueryBuilder user(String userId) {
		query.addUser(userId);
		return this;
	}
	
	public UserQueryBuilder users(List<String> userIds) {
		query.addUsers(userIds);		
		return this;
	}
	
	public UserQueryBuilder name(String name) {
		query.addName(name);
		return this;
	}
	
	public UserQueryBuilder names(Iterable<String> names) {
		for (String name : names) name(name);
		return this;
	}
	
	public UserQueryBuilder loginId(String loginId) {
		if (StringUtils.isEmpty(loginId)) return this;
		
		query.setLoginId(loginId);
		return this;
	}
	
	public UserQueryBuilder jid(String messengerId) {
		if (StringUtils.isEmpty(messengerId)) return this;
		
		query.setMessengerId(messengerId);
		return this;
	}
	
	public UserQueryBuilder companyRole(CompanyRole role) {
		company(role.companyId());
		query.setCompanyRole(role.getCode());
		return this;
	}
	
	public UserQueryBuilder search(String text) {
		query.setSearcher(new Searcher(text));
		return this;
	}
	
	@Override
	public UserQuery build() {
		return query;
	}

	@Override
	protected UserQueryBuilder builder() {
		return this;
	}

}
