package com.lgu.abc.core.prototype.org.company.repo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.department.repo.DepartmentRegistry;
import com.lgu.abc.core.prototype.org.department.support.DepartmentFinder;

@Component
public class CompanyRegistry {
	
	protected static final Log logger = LogFactory.getLog(CompanyRegistry.class);
	
	private static CompanyRepository repository;
	
	private static DepartmentFinder finder;

	@Autowired
	private CompanyRegistry(CompanyRepository repo, DepartmentFinder departmentFinder) {
//		Validate.isTrue(repository == null, "repository should be instantiated only once by Spring.");
//		Validate.isTrue(finder == null, "department finder should be instantiated only once by Spring.");
		
		if (repository == null) repository = repo;
		if (finder == null) finder = departmentFinder;
	}
	
	public static Company get(String companyId) {
		if (companyId == null) return null;
		return repository == null ? null : repository.read(companyId);
	}
	
	public static void evict(String companyId) {
		logger.debug("evict company: " + companyId);
		repository.evict(companyId);
		
		evictAllOrganization(companyId);
	}
	
	public static void evictAllDepartments(String companyId) {
		logger.debug("evict all departments in company: " + companyId);
		
		Iterable<Department> departments = finder.findByCompanyId(companyId);
		if (IterableUtils.isEmpty(departments)) return;
		
		for (Department department : departments) {
			DepartmentRegistry.evict(department.getId());
		}
	}
	
	/**
	 * Evict all caches of organization parties, which includes company, department and user.
	 * @param companyId the id of company to be evicted
	 */
	public static void evictAllOrganization(String companyId) {
		logger.debug("evict all organization units in company: " + companyId);
		
		Iterable<Department> departments = finder.findByCompanyId(companyId);
		if (IterableUtils.isEmpty(departments)) return;
		
		for (Department department : departments) {
			DepartmentRegistry.evict(department.getId());
			DepartmentRegistry.evictAllUser(department.getId());
		}
	}
	
}
