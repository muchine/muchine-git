package com.lgu.abc.core.prototype.org.company.repo;

import com.lgu.abc.core.prototype.base.PartyRegistryTest;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.repo.CompanyRegistry;
import com.lgu.abc.core.prototype.org.company.repo.CompanyRepository;
import com.lgu.abc.test.common.base.fixture.SessionFactory;

public class CompanyRegistryTest extends PartyRegistryTest<Company> {
	
	private final String companyId = SessionFactory.COMPANY_ID;

	@Override
	protected void evict() {
		CompanyRegistry.evict(companyId);
	}

	@Override
	protected Company get() {
		return CompanyRegistry.get(companyId);
	}

	@Override
	protected String id() {
		return companyId;
	}

	@Override
	protected String cacheName() {
		return CompanyRepository.CACHE_NAME;
	}
	
}
