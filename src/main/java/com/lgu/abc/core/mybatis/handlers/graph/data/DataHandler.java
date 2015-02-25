package com.lgu.abc.core.mybatis.handlers.graph.data;

import java.util.List;

import com.lgu.abc.core.common.interfaces.Identifiable;


public interface DataHandler {

	int insert(Object parent, Object entity, String fieldName);

	int update(Object parent, Object entity, String fieldName);

	int delete(Object parent, Object entity, String fieldName);

	void upsert(Object parent, Object entity, String fieldName);
	
	List<Identifiable> findAll(Object parent, String fieldName);
	
	void deleteAll(Object parent, String fieldName);

}