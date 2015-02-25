package com.lgu.abc.core.base.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.mybatis.handlers.file.FileHandler;
import com.lgu.abc.core.support.converter.Converter;
import com.lgu.abc.core.support.converter.ExceptionProcessingConverter;
import com.lgu.abc.core.support.id.IdGenerator;
import com.lgu.abc.core.support.id.IdMonitor;
import com.u2ware.springfield.repository.EntityRepository;

public abstract class DelegatedRepositoryImpl<T extends ActionEntity, P, ID extends Serializable> implements DomainRepository<T, P, ID> {

	protected final Log logger = LogFactory.getLog(this.getClass());

	private final EntityRepository<P, ID> repository;

	private Converter<T, P> converter;

	@Autowired
	private FileHandler fileHandler;

	@Autowired
	private IdGenerator idGenerator;
	
	@Setter
	private IdMonitor idMonitor;

	public DelegatedRepositoryImpl(EntityRepository<P, ID> repository) {
		this.repository = repository;
	}

	@PostConstruct
	public void initialize() {
		this.converter = new ExceptionProcessingConverter<T, P>(initConverter());
	}

	protected abstract Converter<T, P> initConverter();

	@Override
	public T save(T entity) {
		return createOrUpdate(entity);
	}

	@Override
	public void deleteAll(Object query) {
		if (!(repository instanceof BaseRepository)) throw new UnsupportedOperationException();
		((BaseRepository<P, ID>) repository).deleteAll(query);
	}
	
	@Override
	public void rearrange(T entity, int to) {
		if (!(repository instanceof BaseRepository)) throw new UnsupportedOperationException();
		((BaseRepository<P, ID>) repository).rearrange(converter.beanize(entity), to);
	}

	@Override
	public boolean exists(ID id, boolean throwException) {
		return repository.exists(id, throwException);
	}

	@Override
	public boolean exists(T entity, boolean throwException) {
		return repository.exists(converter.beanize(entity), throwException);
	}

	@Override
	public T create(T entity) {
		/*
		 * NOTE File handler populates a bean with stored file information but
		 * domain entity does not have it. So should we have to refresh domain
		 * entity here?
		 */
		if (entity.isNull())
			entity.setId(idGenerator.generateId(entity));

		entity.prepareCreation();
		fileHandler.handleFile(entity, entity.getActor());

		P bean = converter.beanize(entity);
		logger.debug("persisted: " + bean);

		repository.create(bean);
		
		if (entity.isNull()) {
			// handle auto-generated id
			T created = converter.materialize(bean);
			entity.setId(created.getId());
			
			if (idMonitor != null) idMonitor.add(entity.getClass(), created.getId());
		}

		return entity;
	}

	@Override
	public T read(ID id) {
		return converter.materialize(repository.read(id));
	}

	@Override
	public T read(T entity) {
		P bean = converter.beanize(entity);
		T found = converter.materialize(repository.read(bean));
		
		if (found != null) {
			injectActionContext(entity, found);
			found.initialize();
		}
		
		return found;
	}

	@Override
	public T update(T entity) {
		entity.prepareUpdate();
		fileHandler.handleFile(entity, entity.getActor());

		P bean = this.converter.beanize(entity);
		logger.debug("update bean: " + bean);

		repository.update(bean);

		return entity;
	}

	@Override
	public void delete(T entity) {
		repository.delete(converter.beanize(entity));
	}

	@Override
	public T createOrUpdate(T entity) {
		return exists(entity, false) ? update(entity) : create(entity);
	}

	@Override
	public List<T> findAll(Object query) {
		List<P> results = repository.findAll(query);
		return materialize(results);
	}

	@Override
	public List<T> findAll(Object query, Sort sort) {
		List<P> results = repository.findAll(query, sort);
		return materialize(results);
	}

	@Override
	public Page<T> findAll(Object query, Pageable pageable) {
		long total = count(query);
		List<T> contents = new ArrayList<T>();

		if (total > 0) {
			Page<P> results = repository.findAll(query, pageable);
			for (P result : results) {
				T content = converter.materialize(result);
				if (content == null) continue;
				
				injectActionContext(query, content);
				content.initialize();
				contents.add(content);
			}
		}

		return new PageImpl<T>(contents, pageable, total);
	}

	@Override
	public long count(Object query) {
		return repository.count(query);
	}
	
	@Override
	public <X> X getTemplate() {
		return repository.getTemplate();
	}

	protected final List<T> materialize(List<P> results) {
		List<T> found = new ArrayList<T>();
		for (P result : results)
			found.add(this.converter.materialize(result));

		return found;
	}
	
	private void injectActionContext(Object query, T entity) {
		RepositoryUtils.injectActionContext(query, entity);
	}
	
}
