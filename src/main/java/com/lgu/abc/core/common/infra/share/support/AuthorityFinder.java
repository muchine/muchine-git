package com.lgu.abc.core.common.infra.share.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.AuthorityQuery;
import com.lgu.abc.core.common.infra.share.service.AuthorityService;
import com.lgu.abc.core.prototype.org.user.User;

@Component
public class AuthorityFinder {

	@Autowired
	private AuthorityService service;
	
	public Iterable<Authority> findByUserId(String tableName, String userId) {
		AuthorityQuery query = new AuthorityQuery();
		query.findByUserId(tableName, userId);
		
		return service.find(query, null);
	}
	
	public Iterable<Authority> findAllAccessibleAuthorities(String tableName, User user) {
		AuthorityQuery query = new AuthorityQuery();
		query.findAllAccessibleAuthorities(tableName, user);
		
		return service.findAllAccessible(query);
	}
	
}
