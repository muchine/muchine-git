package com.lgu.abc.core.prototype.org.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.base.domain.query.BaseQuery;
import com.lgu.abc.core.common.query.Searcher;
import com.lgu.abc.core.prototype.org.user.query.UserOrganization;
import com.lgu.abc.core.prototype.org.user.query.UserStatus;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class UserQuery extends BaseQuery {

	private UserStatus status = new UserStatus();
	
	private UserOrganization organization; 
	
	private Boolean stopped;
		
	private List<String> names;
	
	private String loginId;
	
	private String messengerId;
	
	private String companyRole;
		
	private Searcher searcher;
	
	@Override
	public void clear() {
		super.clear();
		
		organization = null;
		names = null;
		loginId = null;
		messengerId = null;
	}
	
	public void addUser(String userId) {
		Validate.notNull(userId, "user id is null.");
		
		if (organization == null) organization = new UserOrganization();
		organization.addUser(userId);
	}
	
	public void addUsers(List<String> userIds) {
		if (CollectionUtils.isEmpty(userIds)) return;
		
		if (organization == null) organization = new UserOrganization();
		organization.addUsers(userIds);
	}
		
	public void addDepartment(String departmentId) {
		Validate.notNull(departmentId, "department id is null.");
		
		if (organization == null) organization = new UserOrganization();
		organization.addDepartment(departmentId);
	}
	
	public void addDepartments(List<String> departmentIds) {
		if (CollectionUtils.isEmpty(departmentIds)) return;
		
		if (organization == null) organization = new UserOrganization();
		organization.addDepartments(departmentIds);
	}
	
	public void addName(String name) {
		if (names == null) names = new ArrayList<String>();
		names.add(name);
	}
	
}
