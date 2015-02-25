package com.lgu.abc.core.mybatis.handlers.graph.policy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.utils.EntityUtils;
import com.lgu.abc.core.support.annotation.Bean;

public final class FieldStatementNamePolicy implements StatementNamePolicy {
	
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public String insert(Class<?> parent, String field) {
		return this.getNamespace(parent, field) + ".create";
	}

	@Override
	public String update(Class<?> parent, String field) {
		return this.getNamespace(parent, field) + ".update";
	}

	@Override
	public String delete(Class<?> parent, String field) {
		return this.getNamespace(parent, field) + ".delete";
	}

	@Override
	public String findAll(Class<?> parent, String field) {
		return this.getNamespace(parent, field) + ".findAll";
	}
	
	@Override
	public String deleteAll(Class<?> parent, String field) {
		return this.getNamespace(parent, field) + ".deleteAll";
	}
	
	@Override
	public String getNamespace(Class<?> parent, String fieldName) {
		Bean annotation = parent.getAnnotation(Bean.class);
		String namespace = annotation != null ? annotation.value().getCanonicalName() : EntityUtils.getActualClass(parent).getCanonicalName();				
				
		return namespace + "." + fieldName;
	}
		
}
