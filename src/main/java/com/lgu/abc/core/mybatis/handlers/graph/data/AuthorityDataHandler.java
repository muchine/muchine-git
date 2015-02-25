package com.lgu.abc.core.mybatis.handlers.graph.data;

import java.util.List;

import com.lgu.abc.core.base.domain.action.ShareableEntity;
import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.domain.Accessor;
import com.lgu.abc.core.common.infra.share.repo.AuthorityRepository;
import com.lgu.abc.core.common.interfaces.Identifiable;

public class AuthorityDataHandler implements DataHandler {

	private final AuthorityRepository repository;
	
	public AuthorityDataHandler(AuthorityRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public int insert(Object parent, Object entity, String fieldName) {
		if (!isSharedEntity(parent)) return 0;
		
		ShareableEntity casted = (ShareableEntity) parent;
		Accessor accessor = (Accessor) entity;
		
		if (accessor.getShown() == null) accessor.setShown(true);
		
		String tableName = casted.getShareTableName();
		Authority authority = new Authority(tableName, casted.getId(), accessor);
		authority.writeBaseProperty(casted);
		
		repository.create(authority);
		return 1;
	}

	@Override
	public int update(Object parent, Object entity, String fieldName) {
		if (!isSharedEntity(parent)) return 0;
		
		delete(parent, entity, fieldName);
		insert(parent, entity, fieldName);
		
		return 1;
	}

	@Override
	public int delete(Object parent, Object entity, String fieldName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Identifiable> findAll(Object parent, String fieldName) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void deleteAll(Object parent, String fieldName) {
		if (!isSharedEntity(parent)) return;
		repository.deleteAll(parent);
	}

	@Override
	public void upsert(Object parent, Object entity, String fieldName) {
		throw new UnsupportedOperationException();
	}
	
	private boolean isSharedEntity(Object parent) {
		return ((ShareableEntity) parent).isShared();
	}
	
}
