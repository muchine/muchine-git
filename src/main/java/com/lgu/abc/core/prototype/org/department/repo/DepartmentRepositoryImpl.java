package com.lgu.abc.core.prototype.org.department.repo;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.lgu.abc.core.prototype.org.department.Department;
import com.u2ware.springfield.repository.sqlsession.EntitySqlSessionRepository;

@SuppressWarnings("unchecked")
@Repository
public class DepartmentRepositoryImpl extends EntitySqlSessionRepository<Department, String> implements DepartmentRepository {

	private final String namespace = Department.class.getCanonicalName();
	
	@Autowired
	public DepartmentRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
		super(Department.class, sqlSessionTemplate);
	}
	
	@Override
	public Department read(Department entity) {
		return entity == null ? null : read(entity.getId());
	}
	
	/*
	 * Do not create superclass for this. When @Cacheable annotation is located on the method which is overrided in two depth,
	 * the annotation does not work.
	 */
	@Cacheable(DepartmentRepository.CACHE_NAME)
	@Override
	public Department read(String id) {
		if (id == null) return null;
		return getTemplate().selectOne(namespace + ".read", id);
	}

	@CacheEvict(DepartmentRepository.CACHE_NAME)
	@Override
	public void evict(String id) {}
	
}
