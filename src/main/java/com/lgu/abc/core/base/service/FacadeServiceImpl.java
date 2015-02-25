package com.lgu.abc.core.base.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.lgu.abc.core.support.converter.Converter;
import com.lgu.abc.core.support.converter.ExceptionProcessingConverter;
import com.u2ware.springfield.repository.EntityRepository;
import com.u2ware.springfield.service.EntityServiceImpl;

public abstract class FacadeServiceImpl<T, B, Q> extends EntityServiceImpl<B, Q> implements DomainService<B, Q> {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	protected final DomainService<T, Q> service;
	
	protected final Converter<T, B> converter;
	
	public FacadeServiceImpl(EntityRepository<B, String> repository, DomainService<T, Q> domainService, Converter<T, B> converter) {
		super(repository);
		
		this.service = domainService;
		this.converter = new ExceptionProcessingConverter<T, B>(converter);		
	}

	@Override
	@Transactional
	public B create(B entity) {
		T materialized = converter.materialize(entity);
		return converter.beanize(service.create(materialized));
	}

	@Override
	@Transactional
	public B update(B entity) {
		T materialized = converter.materialize(entity);
		return converter.beanize(service.update(materialized));
	}

	@Override
	@Transactional
	public B delete(B entity) {
		T materialized = converter.materialize(entity);
		return converter.beanize(service.delete(materialized));
	}
	
	@Override
	public B read(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional
	public List<B> create(List<B> entities) {
		if (entities == null) return null;
		
		List<B> result = new ArrayList<B>();
		for (B entity : entities) result.add(this.create(entity));
		
		return result;
	}

	@Override
	@Transactional
	public List<B> update(List<B> entities) {
		if (entities == null) return null;
		
		List<B> result = new ArrayList<B>();
		for (B entity : entities) result.add(this.update(entity));
		
		return result;
	}

	@Override
	@Transactional
	public List<B> delete(List<B> entities) {
		if (entities == null) return null;
		
		List<B> result = new ArrayList<B>();
		for (B entity : entities) result.add(this.delete(entity));
		
		return result;
	}
	
	@Override
	public void deleteAll(Q query) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void rearrange(B entity, int to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long count(Object query) {
		return service.count(query);
	}

	@Override
	public List<B> save(List<B> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public B save(B entity) {
		throw new UnsupportedOperationException();
	}

}
