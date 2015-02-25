package com.lgu.abc.core.prototype.org.department.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.department.DepartmentQuery;
import com.lgu.abc.core.prototype.org.department.repo.DepartmentRegistry;
import com.lgu.abc.core.prototype.org.department.service.DepartmentService;

@Component
public class DepartmentFinder {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private DepartmentService service;
	
	public Department findById(String id) {
		return DepartmentRegistry.get(id);
	}
	
	public Iterable<Department> findByIds(List<String> ids) {
		List<Department> departments = new ArrayList<Department>();
		if (CollectionUtils.isEmpty(ids)) return departments;
		
		for (String id : ids) {
			Department department = DepartmentRegistry.get(id);
			if (department != null) departments.add(department);
		}
		
		return departments;
	}
	
	public Iterable<Department> findByCompanyId(String companyId) {
		DepartmentQuery query = new DepartmentQuery();
		query.setCompanyId(companyId);
		
		return service.find(query, null);
	}
	
	public Iterable<Department> findByParentId(String companyId, String parentId) {
		return service.find(queryByParentId(companyId, parentId), null);
	}
	
	public Iterable<Department> findByName(String companyId, String name) {
		DepartmentQuery query = new DepartmentQuery();
		query.setCompanyId(companyId);
		query.setName(name);
		
		return service.find(query, null);
	}
	
	public long countByParentId(String companyId, String parentId) {
		Long count = service.count(queryByParentId(companyId, parentId));
		return count == null ? 0 : count;
	}
	
	public Department findVirtualDepartment(String companyId) {
		Iterable<Department> departments = findByCompanyId(companyId);
		for (Department department : departments) {
			if (department.isVirtual()) return department;
		}
		
		throw new IllegalStateException("No virtual department exists.");
	}
	
	private DepartmentQuery queryByParentId(String companyId, String parentId) {
		Validate.notNull(companyId, "company id is null.");
		Validate.notNull(parentId, "parent id is null.");
		
		DepartmentQuery query = new DepartmentQuery();
		query.setCompanyId(companyId);
		query.setParentId(parentId);
		
		return query;
	}
	
}
