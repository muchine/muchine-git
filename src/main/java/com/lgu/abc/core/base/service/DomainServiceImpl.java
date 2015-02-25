package com.lgu.abc.core.base.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.plugin.SortableComparator;
import com.lgu.abc.core.base.repository.BaseRepository;
import com.lgu.abc.core.base.service.exception.EntityServiceException;
import com.lgu.abc.core.common.infra.log.Logger;
import com.lgu.abc.core.common.infra.log.LoggerFactory;
import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.plugin.serivce.DomainEventListener;
import com.lgu.abc.core.plugin.serivce.EventListenerRegistry;
import com.u2ware.springfield.domain.EntityPageable;
import com.u2ware.springfield.repository.EntityRepository;
import com.u2ware.springfield.service.EntityServiceImpl;

public abstract class DomainServiceImpl<T, Q> extends EntityServiceImpl<T, Q> implements DomainService<T, Q>, EventListenerRegistry<T> {

	protected Logger logger;
	
	@Autowired
	private LoggerFactory factory;
	
	private List<DomainEventListener<T>> listeners = new ArrayList<DomainEventListener<T>>();
	
	public DomainServiceImpl(EntityRepository<T, ?> repository) {
		super(repository);
	}
	
	@PostConstruct
	public final void initialize() {
		this.logger = factory.getLog(getClass());
	}
	
	@Override
	public Object home(Q query) {
		try {
			return super.home(query);
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<T> findForm(Q query, EntityPageable pageable) {
		try {
			Iterable<T> found = super.findForm(query, pageable);
			for (DomainEventListener<T> e : listeners) e.find(found);
			
			return found;
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<T> find(Q query, EntityPageable pageable) {
		try {
			Iterable<T> found = super.find(query, pageable);
			for (DomainEventListener<T> e : listeners) e.find(found);
			
			return found;
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long count(Object query) {
		try {
			return getRepository().count(query);
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public T read(T entity) {
		try {
			T found = super.read(entity);
			if (found != null && found instanceof RootEntity) ((RootEntity) found).success();
			
			for (DomainEventListener<T> e : listeners) e.read(found);
			
			return found;
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public T read(String id) {
		if (!(getRepository() instanceof BaseRepository)) throw new UnsupportedOperationException();
		return ((BaseRepository<T, String>) getRepository()).read(id);
	}
		
	@Override
	public T createForm(T entity) {
		try {
			return super.createForm(entity);
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}

	@Override
	@Transactional
	public List<T> create(List<T> entities) {
		if (entities == null) return null;
		
		List<T> result = new ArrayList<T>();
		
		for (DomainEventListener<T> e : listeners) e.creating(entities); 
		
		for (T entity : entities)
			result.add(this.create(entity));
		
		for (DomainEventListener<T> e : listeners) e.created(entities);
		
		return result;
	}
	
	@Override
	@Transactional
	public T create(T entity) {
		try {
			for (DomainEventListener<T> e : listeners) e.creating(entity);
			
			logger.debug(entity, "create entity: " + entity);
			T result = super.create(entity);
			if (result instanceof RootEntity) ((RootEntity) result).success();
			logger.debug(entity, "completed to create entity: " + entity);
			
			for (DomainEventListener<T> e : listeners) e.created(result);
			
			return result;
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}
	
	@Override
	@Transactional
	public List<T> save(List<T> entities) {
		if (entities == null) return null;
		
		List<T> result = new ArrayList<T>();
		
		for (T entity : entities)
			result.add(this.save(entity));
		
		return result;
	}
	
	@Override
	@Transactional
	public T save(T entity) {
		try {
			return getRepository().exists(entity, false) ? update(entity) : create(entity);  	
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public T updateForm(T entity) {
		try {
			T found = super.updateForm(entity);
			for (DomainEventListener<T> e : listeners) e.read(found);
			
			return found;
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}
	
	@Override
	@Transactional
	public List<T> update(List<T> entities) {
		if (entities == null) return null;
		
		List<T> result = new ArrayList<T>();
		
		for (DomainEventListener<T> e : listeners) e.updating(entities); 
		
		for (T entity : entities)
			result.add(this.update(entity));
		
		for (DomainEventListener<T> e : listeners) e.updated(entities);
		
		return result;
	}

	@Override
	@Transactional
	public T update(T entity) {
		try {
			T oldEntity = read(entity);
			for (DomainEventListener<T> e : listeners) e.updating(oldEntity, entity);
			
			logger.debug(entity, "update entity: " + entity);
			T result = super.update(entity);
			if (result instanceof RootEntity) ((RootEntity) result).success();
			logger.debug(entity, "completed to update entity: " + entity);
			
			for (DomainEventListener<T> e : listeners) e.updated(oldEntity, result);
			
			return result;
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}
	
	@Override
	@Transactional
	public List<T> delete(List<T> entities) {
		if (entities == null) return null;
		
		List<T> result = new ArrayList<T>();
		
		for (T entity : entities)
			result.add(this.delete(entity));
		
		return result;
	}

	@Override
	@Transactional
	public T delete(T entity) {
		try {
			for (DomainEventListener<T> e : listeners) e.removing(entity);
			
			logger.debug(entity, "delete entity: " + entity);
			T result = super.delete(entity);
			if (result instanceof RootEntity) ((RootEntity) result).success();
			logger.debug(entity, "completed to delete entity: " + entity);

			for (DomainEventListener<T> e : listeners) e.removed(result);
			
			return result;	
		}
		catch (RuntimeException e) {
			throw wrap(e);
		}
	}
	
	@Override
	@Transactional
	public void deleteAll(Q query) {
		if (!(getRepository() instanceof BaseRepository)) throw new UnsupportedOperationException();
		
		BaseRepository<T, ?> repository = (BaseRepository<T, ?>) getRepository();
		repository.deleteAll(query);
	}
	
	@Override
	@Transactional
	public void rearrange(T entity, int to) {
		if (!(getRepository() instanceof BaseRepository)) throw new UnsupportedOperationException();
		((BaseRepository<T, ?>) getRepository()).rearrange(entity, to);
	} 

	/*
	 * Event Listener Methods
	 */
	@Override
	public void addEventListener(DomainEventListener<T> listener) {
		Validate.notNull(listener, "listener is null.");
		Validate.notNull(listener.name(), "listener name is null.");
		
		logger.info("add event listener: " + listener.name());
		
		if (hasEventListener(listener))
			throw new IllegalArgumentException("Listener already exists. lisetner: " + listener);
		
		listeners.add(listener);
		Collections.sort(listeners, SortableComparator.instance());
	}

	@Override
	public void removeEventListener(DomainEventListener<T> listener) {
		logger.info("remove event listener: " + listener.name());
		
		for (int i = 0, n = listeners.size(); i < n; i++) {
			DomainEventListener<T> e = listeners.get(i);
			if (e.name().equals(listener.name())) listeners.remove(i);
		}
	}

	@Override
	public boolean hasEventListener(DomainEventListener<T> listener) {
		for (DomainEventListener<T> e : listeners)
			if (e.name().equals(listener.name())) return true;
		
		return false;
	}
	
	public RuntimeException wrap(RuntimeException e) {
		throw e instanceof CoreException ? e : new EntityServiceException(e);
	}
	
}
