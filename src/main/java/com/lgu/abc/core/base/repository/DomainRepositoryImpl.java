package com.lgu.abc.core.base.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.mybatis.handlers.file.FileHandler;
import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler;
import com.lgu.abc.core.mybatis.handlers.query.impl.GenericQueryResolver;
import com.lgu.abc.core.mybatis.handlers.remover.CascadeRemover;
import com.lgu.abc.core.support.converter.Converter;
import com.lgu.abc.core.support.converter.ExceptionProcessingConverter;
import com.lgu.abc.core.support.id.IdGenerator;

public abstract class DomainRepositoryImpl<T extends ActionEntity, P extends RootEntity, ID extends Serializable> 
		implements DomainRepository<T, P, ID> {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	private final SqlSessionTemplate template;
	
	private final String namespace;
	
	@Getter @Setter
	private Converter<T, P> converter;
	
	@Setter
	private CascadeRemover<T> remover;
	
	@Autowired
	private IdGenerator idGenerator;
		
	@Autowired
	private GenericQueryResolver resolver;
	
	@Autowired
	private AggregateHandler aggregateHandler;
	
	@Autowired
	private FileHandler fileHandler;
		
	public DomainRepositoryImpl(Class<T> entityClass, SqlSessionTemplate template) {
		this.template = template;
		this.namespace = entityClass.getCanonicalName();
	}
	
	@PostConstruct
	public void initialize() {
		this.converter = new ExceptionProcessingConverter<T, P>(initConverter());
		logger.debug("Converter: " + converter);
		Validate.notNull(converter);
	}
	
	protected abstract Converter<T, P> initConverter();
		
	@SuppressWarnings("unchecked")
	@Override
	public SqlSessionTemplate getTemplate() {
		return this.template;
	}

	@Override
	public boolean exists(ID id, boolean throwException) {
		String statement = this.namespace + ".exists";
		Integer result = (Integer)getTemplate().selectOne(statement, id);

		return result == 1;
	}

	@Override
	public boolean exists(T entity, boolean throwException) {
		String statement = this.namespace + ".exists";
		Integer result = (Integer) getTemplate().selectOne(statement, entity.getId());

		return result == 1;
	}

	@Override
	public T create(T entity) {
		Validate.notNull(entity, "entity is null.");
		if (entity.getId() == null)
			entity.setId(idGenerator.generateId(entity));
		
		logger.debug("populated sequence: " + entity.getId());
		
		entity.prepareCreation();

		/*
		 * NOTE File handler populates a bean with stored file information but domain entity does not have it.
		 * So should we have to refresh domain entity here?
		 */
		fileHandler.handleFile(entity, entity.getActor());
		
		String statement = this.namespace + ".create";
		
		P bean = this.converter.beanize(entity);
		logger.debug("persisted: " + bean);
		
		getTemplate().insert(statement, bean);
		this.aggregateHandler.create(bean);
		
		return entity;
	}

	@Override
	public T read(ID id) {
		String statement = this.namespace + ".read";
		P result = getTemplate().selectOne(statement, id);
		
		logger.debug("read entity by id. statemet: " + statement + ", found: " + result);
				
		return this.converter.materialize(result);
	}

	@Override
	public T read(T entity) {
		String statement = this.namespace + ".read";
		P result = getTemplate().selectOne(statement, entity.getId());
		
		logger.debug("read entity by entity. statemet: " + statement + ", found: " + result);
		
		return this.converter.materialize(result);
	}

	@Override
	public T update(T entity) {
		Validate.notNull(entity, "entity is null.");
		entity.prepareUpdate();
		fileHandler.handleFile(entity, entity.getActor());
		
		String statement = this.namespace + ".update";
		P bean = this.converter.beanize(entity);
		
		logger.debug("update bean: " + bean);
				
		int result = getTemplate().update(statement, bean);
		if(result == 1){
			this.aggregateHandler.update(bean);
			return entity;
		}else{
			throw new InvalidDataAccessResourceUsageException("update");
		}
	}

	@Override
	public void delete(T entity) {
		if (this.remover != null) {
			logger.info("Delete cascade for entity: " + entity);
			remover.removeCascade(entity);
		}
		
		P bean = this.converter.beanize(entity);
		this.aggregateHandler.delete(bean);

		String statement = this.namespace + ".delete";
		int result = getTemplate().delete(statement, entity.getId());
		
		if(result != 1){
			throw new InvalidDataAccessResourceUsageException("delete");
		}
	}
	
	@Override
	public void deleteAll(Object query) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("query", query);
		
		this.getTemplate().delete(this.getNamespace() + ".deleteAll", param);
	}

	@Override
	public T createOrUpdate(T entity) {
		return exists(entity , false) ? update(entity) : create(entity);
	}

	@Override
	public List<T> findAll(Object query) {
		this.resolver.resolve(query);
		
		String statement =  this.namespace + ".findAll";
		Map<String,Object> param = quessQueryParameter(query, null, null);
		List<P> results = getTemplate().selectList(statement, param);
		
		List<T> found = new ArrayList<T>();
		
		for (P result : results)
			found.add(this.converter.materialize(result));
				
		return found;
	}

	@Override
	public List<T> findAll(Object query, Sort sort) {
		this.resolver.resolve(query);
		
		String statement =  this.namespace + ".findAll";
		Map<String,Object> param = quessQueryParameter(query, null, sort);
		List<P> results = getTemplate().selectList(statement, param);
		
		List<T> found = new ArrayList<T>();
		for (P result : results)
			found.add(this.converter.materialize(result));
				
		return found;
	}
	
	@Override
	public Page<T> findAll(Object query, Pageable pageable) {
		long total = count(query);
		List<T> contents = new ArrayList<T>();
		
		if(total > 0){
			String statement = this.namespace + ".findAll";
			Map<String,Object> param = quessQueryParameter(query, pageable, null);
			
			int offset = pageable.getPageSize() * pageable.getPageNumber();
			int limit  = pageable.getPageSize();
			List<P> results = getTemplate().selectList(statement, param, new RowBounds(offset, limit));
			
			contents = new ArrayList<T>();
			for (P result : results)
				contents.add(this.converter.materialize(result));
		}

		return new PageImpl<T>(contents, pageable, total);
	}
	
	@Override
	public long count(Object query) {
		String statement = this.namespace + ".findAllCount";
		Map<String,Object> param = quessQueryParameter(query, null, null);
		
		Long total = getTemplate().selectOne(statement,  param);
		return total;
	}

	@Override
	public T save(T entity) {
		return this.createOrUpdate(entity);
	}
	
	public String getNamespace() {
		return this.namespace;
	}
	
	private Map<String,Object> quessQueryParameter(Object query, Pageable pageable, Sort sort){
		Map<String,Object> param = new HashMap<String,Object>();
		
		param.put("query", query);
		param.put("pageable", pageable);
		param.put("sort", sort);
		
		return param;
	}
	
}
