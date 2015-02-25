package com.lgu.abc.core.prototype.workspace.repo;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.lgu.abc.core.prototype.workspace.Workspace;
import com.u2ware.springfield.repository.sqlsession.EntitySqlSessionRepository;

@SuppressWarnings("unchecked")
@Repository
public class WorkspaceRepositoryImpl extends EntitySqlSessionRepository<Workspace, String> implements WorkspaceRepository {
	
	private final String namespace = Workspace.class.getCanonicalName();
	
	@Autowired
	public WorkspaceRepositoryImpl(SqlSessionTemplate template) {
		super(Workspace.class, template);
	}
	
	@Override
	public Workspace read(Workspace entity) {
		return entity == null ? null : read(entity.getId());
	}
	
	/*
	 * Do not create superclass for this. When @Cacheable annotation is located on the method which is overrided in two depth,
	 * the annotation does not work.
	 */
	@Cacheable(WorkspaceRepository.CACHE_NAME)
	@Override
	public Workspace read(String id) {
		if (id == null) return null;
		Workspace found = getTemplate().selectOne(namespace + ".read", id);
		if (found != null) found.initialize();
		
		return found;
	}

	@CacheEvict(WorkspaceRepository.CACHE_NAME)
	@Override
	public void evict(String id) {}
	
}
