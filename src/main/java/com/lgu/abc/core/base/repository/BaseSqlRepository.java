package com.lgu.abc.core.base.repository;

import java.io.Serializable;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.mybatis.handlers.file.FileHandler;
import com.lgu.abc.core.mybatis.handlers.remover.CascadeRemover;

@SuppressWarnings("unchecked")
public abstract class BaseSqlRepository<T extends ActionEntity, ID extends Serializable> extends AbstractSqlRepository<T, String> {

	@Autowired
	private FileHandler fileHandler;
	
	public BaseSqlRepository(Class<T> entityClass, SqlSessionTemplate sqlSessionTemplate) {
		super(entityClass, sqlSessionTemplate);
	}
	
	public BaseSqlRepository(Class<T> entityClass, SqlSessionTemplate sqlSessionTemplate, CascadeRemover<T> remover) {
		super(entityClass, sqlSessionTemplate, remover);
	}

	@Override
	protected void preHandle(T entity) {
		fileHandler.handleFile(entity, entity.getActor());
	}
	
}
