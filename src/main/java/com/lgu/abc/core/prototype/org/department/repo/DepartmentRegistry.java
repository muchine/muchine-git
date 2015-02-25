package com.lgu.abc.core.prototype.org.department.repo;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.department.DepartmentQuery;
import com.lgu.abc.core.prototype.org.department.NullDepartment;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.repo.UserRegistry;
import com.lgu.abc.core.prototype.org.user.support.UserFinder;

@Component
public class DepartmentRegistry {

	protected static final Log logger = LogFactory.getLog(DepartmentRegistry.class);
	
	private static DepartmentRepository repository;
	
	private static UserFinder finder;
	
	@Autowired
	public DepartmentRegistry(DepartmentRepository repo, UserFinder userFinder) {
		Validate.isTrue(repository == null, "repository should be instantiated only once by Spring.");
		Validate.isTrue(finder == null, "user finder should be instantiated only once by Spring.");
		
		repository = repo;
		finder = userFinder;
	}
	
	public static Department get(String departmentId) {
		logger.trace("get department: " + departmentId);

		if (departmentId == null) return null;
		if (Department.NULL_DEPARTMENT.equals(departmentId)) return NullDepartment.instance();
		
		return repository == null ? null : repository.read(departmentId);
	}
	
	public static void evict(String departmentId) {
		logger.debug("evict department: " + departmentId);
		repository.evict(departmentId);
	}
	
	public static void evict(DepartmentQuery query) {
		List<Department> found = repository.findAll(query);
		
		if (found == null) return;
		for (Department department : found) evict(department.getId());
	}
	
	public static void evictChildren(String companyId, String departmentId) {
		DepartmentQuery query = new DepartmentQuery();
		query.setCompanyId(companyId);
		query.setParentId(departmentId);
		
		evict(query);
	}
	
	public static void evictAllUser(String departmentId) {
		logger.debug("evict all users in department: " + departmentId);
		
		Iterable<User> users = finder.findByDepartmentId(departmentId);
		if (users == null) return;
		
		for (User user : users) UserRegistry.evict(user.getId());
	}
	
}
