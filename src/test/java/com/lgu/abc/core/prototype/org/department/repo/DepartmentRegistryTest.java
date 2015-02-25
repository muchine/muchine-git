package com.lgu.abc.core.prototype.org.department.repo;

import com.lgu.abc.core.prototype.base.PartyRegistryTest;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.department.repo.DepartmentRegistry;
import com.lgu.abc.core.prototype.org.department.repo.DepartmentRepository;

public class DepartmentRegistryTest extends PartyRegistryTest<Department> {

	@Override
	protected void evict() {
		DepartmentRegistry.evict(departmentId());
	}

	@Override
	protected Department get() {
		return DepartmentRegistry.get(departmentId());
	}

	@Override
	protected String id() {
		return departmentId();
	}

	@Override
	protected String cacheName() {
		return DepartmentRepository.CACHE_NAME;
	}
	
	private String departmentId() {
		return getSession().getSelectedDepartment().getId();
	}

}
