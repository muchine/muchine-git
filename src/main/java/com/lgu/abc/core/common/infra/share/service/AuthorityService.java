package com.lgu.abc.core.common.infra.share.service;

import java.util.List;

import com.lgu.abc.core.base.service.DomainService;
import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.AuthorityQuery;

public interface AuthorityService extends DomainService<Authority, AuthorityQuery> {

	List<Authority> findAllAccessible(AuthorityQuery query);
	
}
