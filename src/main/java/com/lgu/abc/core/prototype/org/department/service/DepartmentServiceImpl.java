package com.lgu.abc.core.prototype.org.department.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.base.service.DomainServiceImpl;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.department.DepartmentQuery;
import com.lgu.abc.core.prototype.org.department.repo.DepartmentRepository;

@Service
public class DepartmentServiceImpl extends DomainServiceImpl<Department, DepartmentQuery> implements DepartmentService {

	@Autowired
	public DepartmentServiceImpl(DepartmentRepository repository) {
		super(repository);
	}
	
}
