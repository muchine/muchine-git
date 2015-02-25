package com.lgu.abc.core.common.infra.share.repo;

import java.util.List;

import com.lgu.abc.core.base.repository.BaseRepository;
import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.AuthorityQuery;

public interface AuthorityRepository extends BaseRepository<Authority, String> {

	List<Authority> findAllAccessible(AuthorityQuery query);
	
}
