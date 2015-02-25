package com.lgu.abc.core.mybatis.handlers.graph.strategy.file;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.collection.DeleteInsertStrategy;
import com.lgu.abc.core.support.annotation.file.Files;

public class FileStrategy implements AggregateStrategy {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private final AggregateStrategy strategy;
	
	public FileStrategy(DataHandler handler) {
		this.strategy = new DeleteInsertStrategy(handler);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void create(Object parent, Object entities, String fieldName) {
		logger.debug("create file mappings...");
		strategy.create(parent, maps(parent, (List<File>) entities), fieldName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Object parent, Object entities, String fieldName) {
		logger.debug("update file mappings... file: " + entities);
		strategy.update(parent, maps(parent, (List<File>) entities), fieldName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(Object parent, Object entities, String fieldName) {
		logger.debug("delete file mappings...");
		strategy.delete(parent, maps(parent, (List<File>) entities), fieldName);
	}

	@Override
	public boolean canSupport(Field field) {
		/*
		 * NOTE if entity has one-to-one relationship with attached files, do nothing here.
		 * Entity SQL mapper will handle the association.
		 */
		return field.isAnnotationPresent(Files.class);
	}
	
	private List<FileEntityMap> maps(Object parent, List<File> entities) {
		if (entities == null) return null;
		
		Assert.isTrue(entities instanceof Iterable, "entities does not support iterable.");
		Assert.isTrue(parent instanceof BaseEntity, "parent object is not BaseEntity type.");
		
		List<FileEntityMap> map = new ArrayList<FileEntityMap>();
		
		for (File e : entities) {
			Assert.isTrue(e instanceof Identifiable, "an element in entities is not Identifiable type.");
			map.add(new FileEntityMap((BaseEntity) parent, e.getId()));
		}
		
		return map;
	}

}
