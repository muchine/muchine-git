package com.lgu.abc.core.plugin.proto.department.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class DepartmentRoleMapper extends RootEntity {

	private User user;
	
	private Department department;
	
	public DepartmentRoleMapper() {}
	
	public DepartmentRoleMapper(User user, Department department) {
		assign(user, department);
	}
	
	private void assign(User user, Department department) {
		Validate.isTrue(department == null || !department.isNull());
		
		user.load();
		if (department != null) {
			department.load();			
			Validate.isTrue(department.getCompany().identical(user.getCompany()));
		}
		
		this.user = user;
		this.department = department;
	}

	public User getUser() {
		if (user != null) user.load();
		return user;
	}
	
	public Department getDepartment() {
		if (department != null) department.load();
		return department;
	}

	@Override
	public boolean identical(Identifiable object) {
		if (object == null || !object.getClass().equals(getClass())) return false;
		
		DepartmentRoleMapper casted = (DepartmentRoleMapper) object;
		return casted.getUser().identical(getUser()) && identical(casted.getDepartment(), getDepartment());
	}
	
	public boolean matches(User user, Department department) {
		return getUser().identical(user) && getDepartment().identical(department);
	}
	
	private boolean identical(Department d1, Department d2) {
		if (d1 == null && d2 == null) return true;
		return d1 != null && d1.identical(d2);
	}
	
}
