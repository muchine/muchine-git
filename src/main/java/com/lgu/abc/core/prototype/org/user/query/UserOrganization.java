package com.lgu.abc.core.prototype.org.user.query;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.util.CollectionUtils;

public class UserOrganization {

	@Getter
	private final List<String> userIds = new ArrayList<String>();
	
	@Getter
	private final List<String> departmentIds = new ArrayList<String>();
	
	public UserOrganization() {}
		
	public UserOrganization(List<String> userIds, List<String> departmentIds) {
		Validate.isTrue(!isEmpty(userIds) || !isEmpty(departmentIds), "all arguments are empty.");
		
		addUsers(userIds);
		addDepartments(departmentIds);
	}
	
	public void addUser(String userId) {
		if (StringUtils.isEmpty(userId)) return;
		this.userIds.add(userId);
	}
	
	public void addUsers(List<String> userIds) {
		if (CollectionUtils.isEmpty(userIds)) return;
		for (String userId : userIds) {
			addUser(userId);
		}
	}
	
	public void addDepartment(String departmentId) {
		if (StringUtils.isEmpty(departmentId)) return;
		this.departmentIds.add(departmentId);
	}
	
	public void addDepartments(List<String> departmentIds) {
		if (CollectionUtils.isEmpty(departmentIds)) return;
		for (String departmentId : departmentIds) {
			addDepartment(departmentId);
		}
	}
	
	private boolean isEmpty(List<String> ids) {
		return CollectionUtils.isEmpty(ids);
	}
	
}
