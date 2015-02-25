package com.lgu.abc.core.base.finder;

import java.util.ArrayList;
import java.util.List;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.query.AuthorizableQuery;
import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.prototype.org.user.User;
import com.u2ware.springfield.service.EntityService;

public abstract class AuthorizableFinder<T extends RootEntity, Q extends AuthorizableQuery> extends AbstractFinder<T> {
	
	private final EntityService<T, Q> service;
	
	public AuthorizableFinder(EntityService<T, Q> service) {
		this.service = service;
	}

	public List<T> findAllReadable(User user) {
		Q query = createQuery();
		query.setActor(user);
		query.findAllReadable();
		
		Iterable<T> found = service.find(query, null);
		return createAuthorizedList(found, query);
	}
	
	public List<T> findAllWritable(User user) {
		Q query = createQuery();
		query.setActor(user);
		query.findAllWritable();
		
		Iterable<T> found = service.find(query, null);
		return createAuthorizedList(found, query);
	}
	
	public List<T> findAllManageable(User user) {
		Q query = createQuery();
		query.setActor(user);
		query.findAllManageable();
		
		Iterable<T> found = service.find(query, null);
		return createAuthorizedList(found, query);
	}
	
	private List<T> createAuthorizedList(Iterable<T> found, Q query) {
		List<T> result = new ArrayList<T>();
		if (IterableUtils.isEmpty(found)) return result;
		
		for (T element : found) {
			if (query.canQuery(element)) result.add(element);
		}
		
		return result;
	}
	
	protected abstract Q createQuery();
	
}
