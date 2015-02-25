package com.lgu.abc.core.prototype.org.department.repo;

import com.lgu.abc.core.prototype.base.repo.PrototypeRepository;
import com.lgu.abc.core.prototype.org.department.Department;

public interface DepartmentRepository extends PrototypeRepository<Department> {

	public static final String CACHE_NAME = "departments";
	
}
