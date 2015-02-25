package com.lgu.abc.core.common.infra.option.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.base.service.DomainServiceImpl;
import com.lgu.abc.core.common.infra.option.company.GenericCompanyOption;
import com.lgu.abc.core.common.infra.option.company.repo.GenericCompanyOptionRepository;

@Service
public class GenericCompanyOptionServiceImpl extends DomainServiceImpl<GenericCompanyOption, GenericCompanyOption> implements GenericCompanyOptionService {

	@Autowired
	public GenericCompanyOptionServiceImpl(GenericCompanyOptionRepository repository) {
		super(repository);
	}

}
