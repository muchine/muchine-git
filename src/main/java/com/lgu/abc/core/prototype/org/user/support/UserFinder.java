package com.lgu.abc.core.prototype.org.user.support;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.security.role.company.CompanyAdministrator;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.service.UserService;

@Component
public class UserFinder extends AbstractUserFinder<User> {
	
	@Autowired
	public UserFinder(UserService service) {
		super(service);
	}
	
	public Iterable<User> findByNames(String companyId, Collection<String> names) {
		Validate.noNullElements(names, "names list has null element.");
		
		UserQueryBuilder builder = new UserQueryBuilder()
			.company(companyId)
			.names(names);
		
		return service.find(builder.build(), null);
	}
	
	public User findCompanyAdministrator(Company company) {
		Iterable<User> users = findCompanyAdministrators(company.getId());
		return users.iterator().next();
	}
	
	public Iterable<User> findCompanyAdministrators(String companyId) {
		return findByCompanyRole(new CompanyAdministrator(companyId));
	}
	
	public User findDepartmentManager(Department department) {
		Iterable<User> users = findByDepartmentId(department.getId());
		for (User user : users) {
			if (UserRoleMiner.isDepemartmentManager(user)) return user;
		}
		
		return null;
	}

	@Override
	protected User create(String id) {
		return new User(id);
	}	
		
}
