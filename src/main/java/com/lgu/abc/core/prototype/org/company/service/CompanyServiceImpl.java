package com.lgu.abc.core.prototype.org.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.base.service.DomainServiceImpl;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.CompanyQuery;
import com.lgu.abc.core.prototype.org.company.repo.CompanyRepository;

@Service
public class CompanyServiceImpl extends DomainServiceImpl<Company, CompanyQuery> implements CompanyService {

	@Autowired
	public CompanyServiceImpl(CompanyRepository repository) {
		super(repository);
	}
	
}
