package com.lgu.abc.core.common.infra.share.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.base.service.DomainServiceImpl;
import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.AuthorityQuery;
import com.lgu.abc.core.common.infra.share.repo.AuthorityRepository;

@Service
public class AuthorityServiceImpl extends DomainServiceImpl<Authority, AuthorityQuery> implements AuthorityService {

	private AuthorityRepository repository;
	
	@Autowired
	public AuthorityServiceImpl(AuthorityRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public List<Authority> findAllAccessible(AuthorityQuery query) {
		return repository.findAllAccessible(query);
	}

}
