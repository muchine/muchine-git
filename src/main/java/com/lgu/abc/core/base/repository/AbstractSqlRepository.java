package com.lgu.abc.core.base.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.base.domain.query.BaseQuery;
import com.lgu.abc.core.common.interfaces.Rearrangeable;
import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler;
import com.lgu.abc.core.mybatis.handlers.query.impl.GenericQueryResolver;
import com.lgu.abc.core.mybatis.handlers.remover.CascadeRemover;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.id.IdGenerator;
import com.u2ware.springfield.repository.sqlsession.EntitySqlSessionRepository;

@SuppressWarnings("unchecked")
public abstract class AbstractSqlRepository<T extends ActionEntity, ID extends Serializable> implements BaseRepository<T, String> {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	private EntitySqlSessionRepository<T, ID> repository;
	
	private CascadeRemover<T> remover;
	
	@Getter
	private String namespace;
	
	@Autowired
	private IdGenerator idGenerator;
	
	@Autowired
	private GenericQueryResolver resolver;
	
	@Autowired
	private AggregateHandler aggregateHandler;
			
	public AbstractSqlRepository(Class<T> entityClass, SqlSessionTemplate sqlSessionTemplate) {
		this.repository = new EntitySqlSessionRepository<T, ID>(entityClass, sqlSessionTemplate);
		this.namespace = entityClass.getCanonicalName();
	}
	
	public AbstractSqlRepository(Class<T> entityClass, SqlSessionTemplate sqlSessionTemplate, CascadeRemover<T> remover) {
		this(entityClass, sqlSessionTemplate);
		this.remover = remover;
	}

	@Override
	public SqlSessionTemplate getTemplate() {
		return repository.getTemplate();
	}

	@Override
	public boolean exists(String id, boolean throwException) {
		final String statement = namespace + ".exists";
		Integer result = (Integer)getTemplate().selectOne(statement, id);

		return result == 1;
	}

	@Override
	public boolean exists(T entity, boolean throwException) {
		return repository.exists(entity, throwException);
	}

	@Override
	public T create(T entity) {
		Validate.notNull(entity, "entity is null.");
		if (entity.isNull())
			entity.setId(idGenerator.generateId(entity));
		
		logger.debug("populated sequence: " + entity.getId());
		
		entity.prepareCreation();
		
		preHandle(entity);
		repository.create(entity);
		aggregateHandler.create(entity);
		
		return entity;
	}

	@Override
	public T read(String id) {
		final String statement = this.namespace + ".read";
		T result = getTemplate().selectOne(statement, id);
		
		logger.trace("read entity by id. statemet: " + statement + ", found: " + result);
		
		if (result != null) result.initialize();		
		
		return result;
	}

	@Override
	public T read(T entity) {
		logger.trace("read entity: " + entity);
		T found = repository.read(entity);
		
		if (found != null) {
			injectActionContext(entity, found);
			enrich(getActorFrom(entity), found);
			found.initialize();
		}
		
		return found;
	}

	@Override
	public T update(T entity) {
		Validate.notNull(entity, "entity is null.");
		entity.prepareUpdate();
		
		preHandle(entity);
		repository.update(entity);
		aggregateHandler.update(entity);
		
		return entity;
	}

	@Override
	public void delete(T entity) {
		if (remover != null) {
			logger.info("delete cascade for entity: " + entity);
			remover.removeCascade(entity);
		}
		
		aggregateHandler.delete(entity);
		
		if (entity instanceof Rearrangeable) rearrange(entity, Rearrangeable.MAX_ORDER);
		repository.delete(entity);
	}
	
	@Override
	public void deleteAll(Object query) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("query", query);
		
		logger.debug("delete all... query: " + query);
		
		getTemplate().delete(this.namespace + ".deleteAll", param);
	}

	@Override
	public T createOrUpdate(T entity) {
		return exists(entity , false) ? update(entity) : create(entity);
	}

	@Override
	public List<T> findAll(Object query) {
		resolver.resolve(query);
		
		List<T> found = this.repository.findAll(query);
		initializeEntities(query, found);
		
		return found;
	}

	@Override
	public List<T> findAll(Object query, Sort sort) {
		resolver.resolve(query);
		
		List<T> found = this.repository.findAll(query, sort);
		initializeEntities(query, found);
		
		return found;
	}
	
	@Override
	public Page<T> findAll(Object query, Pageable pageable) {
		if (query instanceof BaseQuery) 
			((BaseQuery) query).setPageable(pageable);
		
		List<T> found = findAll(query);
		initializeEntities(query, found);
		
		return new PageImpl<T>(found == null ? new ArrayList<T>() : found, pageable, count(query));
	}

	@Override
	public long count(Object query) {
		return repository.count(query);
	}

	@Override
	public T save(T entity) {
		return createOrUpdate(entity);
	}
	
	@Override
	public void rearrange(T entity, int to) {
		Validate.isTrue(entity instanceof Rearrangeable, "entity is not rearrangeable object.");
		
		logger.debug("rearrange entity[id=" + entity.getId() + "] to " + to);
		
		final String statement = this.namespace + ".rearrange";
		Rearrangeable rearrangeable = (Rearrangeable) entity;
		
		final int from = rearrangeable.order();
		if (from == to) return;
		
		try {
			int updated = getTemplate().update(statement, rearrangeable.rearrange(to));
			logger.debug(updated + " entities have been rearranged.");
			update(entity);	
		}
		catch (RuntimeException e) {
			rearrangeable.order(from);
			throw e;
		}		
	}
	
	/*
	 * Helper methods
	 */
	private void initializeEntities(Object query, List<T> found) {
		RepositoryUtils.initializeEntities(query, found);
		enrich(getActorFrom(query), found);
	}
	
	private void injectActionContext(Object query, T entity) {
		RepositoryUtils.injectActionContext(query, entity);
	}
	
	private User getActorFrom(Object object) {
		return RepositoryUtils.getActorFrom(object);
	}
		
	protected void enrich(User actor, T entity) {
		// Hook this method when enrich retrieved entity via find() and read().
	}
	
	protected void enrich(User actor, List<T> entities) {
		// Hook this method when enrich retrieved entity via find() and read().
	}
	
	protected abstract void preHandle(T entity);
		
}
