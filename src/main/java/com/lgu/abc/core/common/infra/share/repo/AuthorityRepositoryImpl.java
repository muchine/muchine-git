package com.lgu.abc.core.common.infra.share.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lgu.abc.core.base.repository.BaseSqlRepository;
import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.AuthorityQuery;

@SuppressWarnings("unchecked")
@Repository
public class AuthorityRepositoryImpl extends BaseSqlRepository<Authority, String> implements AuthorityRepository {

	@Autowired
	public AuthorityRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
		super(Authority.class, sqlSessionTemplate);
	}

	@Override
	public List<Authority> findAllAccessible(AuthorityQuery query) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("query", query);
		
		logger.debug("query: " + query);
		return getTemplate().selectList(getNamespace() + ".findAllAccessible", param);
	}

}
