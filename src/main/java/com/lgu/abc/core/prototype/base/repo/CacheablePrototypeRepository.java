package com.lgu.abc.core.prototype.base.repo;

import org.mybatis.spring.SqlSessionTemplate;

import com.lgu.abc.core.common.interfaces.Identifiable;
import com.u2ware.springfield.repository.sqlsession.EntitySqlSessionRepository;

@SuppressWarnings("unchecked")
public abstract class CacheablePrototypeRepository<T extends Identifiable> extends EntitySqlSessionRepository<T, String> implements 
		PrototypeRepository<T> {

	private final String namespace;
	
	public CacheablePrototypeRepository(Class<T> entityClass, SqlSessionTemplate template) {
		super(entityClass, template);
		this.namespace = entityClass.getCanonicalName();
	}
	
	protected String getNamespace() {
		return namespace;
	}
	
	@Override
	public T read(T entity) {
		return entity == null ? null : read(entity.getId());
	}

	@Override
	public T read(String id) {
		return null;
	}

	@Override
	public abstract void evict(String id);

}
