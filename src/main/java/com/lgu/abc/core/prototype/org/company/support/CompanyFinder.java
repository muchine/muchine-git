package com.lgu.abc.core.prototype.org.company.support;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.CompanyQuery;
import com.lgu.abc.core.prototype.org.company.repo.CompanyRegistry;
import com.lgu.abc.core.prototype.org.company.service.CompanyService;

@Component
public class CompanyFinder {

	@Autowired
	private CompanyService service;
	
	public Iterable<Company> findAll() {
		return service.find(new CompanyQuery(), null);
	}
	
	public Company findById(String companyId) {
		return CompanyRegistry.get(companyId);
	}
	
	public Company findByAccessDomain(String domain) {
		Validate.notNull(domain, "company domain is null.");
		CompanyQuery query = new CompanyQuery();
		query.setDomain(domain);
		
		Iterable<Company> found = service.find(query, null);
		return IterableUtils.isEmpty(found) ? null : found.iterator().next();
	}
	
	public Company findByPrimaryDomain(String primaryDomain) {
		Validate.notNull(primaryDomain, "primary domain is null.");
		CompanyQuery query = new CompanyQuery();
		query.setPrimaryDomain(primaryDomain);
		
		Iterable<Company> found = service.find(query, null);
		return IterableUtils.isEmpty(found) ? null : found.iterator().next();
	}
	
	public Company findByPrimaryOrAccessDomain(String domain) {
		Company found = findByPrimaryDomain(domain);
		if (found == null) found = findByAccessDomain(domain);
		
		return found;
	}
	
}
