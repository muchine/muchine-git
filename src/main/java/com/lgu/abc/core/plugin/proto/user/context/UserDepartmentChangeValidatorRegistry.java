package com.lgu.abc.core.plugin.proto.user.context;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class UserDepartmentChangeValidatorRegistry {
	
	private static final Set<UserDepartmentChangeValidatable> validatables = new HashSet<UserDepartmentChangeValidatable>();
	
	public synchronized void add(UserDepartmentChangeValidatable validatable) {
		PluggableUtils.add(validatable, validatables);
	}
	
	public UserDepartmentChangeValidatable find(String validatorId, Company company) {
		return PluggableUtils.find(validatorId, company, validatables);
	}
	
	public List<UserDepartmentChangeValidatable> enabled(Company company) {
		return PluggableUtils.enabled(company, validatables);
	}
		
}
