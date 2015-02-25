package com.lgu.abc.core.base.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.u2ware.springfield.domain.EntityPageable;

public class NullServiceImpl<T, Q> implements DomainService<T, Q> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Object home(Q query) {
		return query;
	}

	@Override
	public Iterable<T> findForm(Q query, EntityPageable pageable) {
		return new ArrayList<T>();
	}

	@Override
	public Iterable<T> find(Q query, EntityPageable pageable) {
		return new ArrayList<T>();
	}

	@Override
	public T read(T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T createForm(T entity) {
		return entity;
	}

	@Override
	public T create(T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T updateForm(T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T update(T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T delete(T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T read(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> save(List<T> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T save(T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> create(List<T> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> update(List<T> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> delete(List<T> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAll(Q query) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long count(Object query) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rearrange(T entity, int to) {
		throw new UnsupportedOperationException();
	}

}
