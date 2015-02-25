package com.lgu.abc.core.prototype.org.company.repo;

import com.lgu.abc.core.prototype.base.repo.PrototypeRepository;
import com.lgu.abc.core.prototype.org.company.Company;

public interface CompanyRepository extends PrototypeRepository<Company> {

	public static final String CACHE_NAME = "companies";
	
}
