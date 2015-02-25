package com.lgu.abc.core.base.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.interfaces.LazyLoadable;

public final class AggregateUtils {
	
	protected static final Log logger = LogFactory.getLog(AggregateUtils.class);
	
	public static <T extends Identifiable> T find(String id, Iterable<T> aggregate) {
		if (StringUtils.isEmpty(id) || aggregate == null) return null;
		
		for (T object : aggregate) {
			if (id.equals(object.getId())) return object;
		}
		
		return null;
	}
	
	public static <T extends Identifiable> T find(T query, Iterable<T> aggregate) {
		return find(query.getId(), aggregate);
	}
	
	public static <T extends Identifiable> boolean contains(T entity, Iterable<T> collection) {
		return find(entity, collection) != null;
	}
		
	public static <T extends Identifiable> void put(T object, List<T> aggregate) {
		Validate.notNull(object);
		
		remove(object.getId(), aggregate);
		aggregate.add(object);
	}
	
	public static <T extends Identifiable> void remove(T object, List<T> aggregate) {
		aggregate.remove(object);
	}
	
	public static <T extends Identifiable> void remove(String id, List<T> aggregate) {
		T object = find(id, aggregate);
		remove(object, aggregate);
	}
	
	public static <T extends LazyLoadable> void load(List<T> aggregate) {
		if (aggregate == null) return;
		
		for (T object : aggregate) {
			object.load();
		}
	}
	
	public static <T extends Identifiable> List<T> getRemovedEntities(Iterable<T> oldEntities, Iterable<T> newEntities) {
		List<T> removed = new ArrayList<T>();
		for (T entity : oldEntities) {
			if (!contains(entity, newEntities)) removed.add(entity);
		}
		
		return removed;
	}
	
	public static <T extends Identifiable> List<T> getAddedEntities(Iterable<T> oldEntities, Iterable<T> newEntities) {
		List<T> added = new ArrayList<T>();
		for (T entity : newEntities) {
			if (!contains(entity, oldEntities)) added.add(entity);
		}
		
		return added;
	}
	
	public static <T extends Identifiable> List<T> getUnchangedEntities(Iterable<T> oldEntities, Iterable<T> newEntities) {
		List<T> unchanged = new ArrayList<T>();
		for (T entity : newEntities) {
			if (contains(entity, oldEntities)) unchanged.add(entity);
		}
		
		return unchanged;
	}
	
	public static <T extends Identifiable> boolean isAggregateChanged(Iterable<T> oldAggregate, Iterable<T> newAggregate) {
		List<T> oldEntities = IterableUtils.toList(oldAggregate);
		List<T> newEntities = IterableUtils.toList(newAggregate);
		
		if (oldEntities.size() != newEntities.size()) return true;
		
		List<T> added = getAddedEntities(oldEntities, newEntities);
		return !CollectionUtils.isEmpty(added);
	}
	
	public static <T extends Identifiable> List<String> makeIdList(Iterable<T> aggregate) {
		List<String> objectIds = new ArrayList<String>();
		if (IterableUtils.isEmpty(aggregate)) return objectIds;
		
		for (T object : aggregate) {
			objectIds.add(object.getId());
		}
		
		return objectIds;
	}
	
}
