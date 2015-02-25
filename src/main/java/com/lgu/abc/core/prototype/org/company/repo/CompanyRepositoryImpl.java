package com.lgu.abc.core.prototype.org.company.repo;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.lgu.abc.core.prototype.org.company.Company;
import com.u2ware.springfield.repository.sqlsession.EntitySqlSessionRepository;

@SuppressWarnings("unchecked")
@Repository
public class CompanyRepositoryImpl extends EntitySqlSessionRepository<Company, String> implements CompanyRepository {
	
	private final String namespace = Company.class.getCanonicalName();
	
	@Autowired
	public CompanyRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
		super(Company.class, sqlSessionTemplate);
	}
	
	@Override
	public Company read(Company entity) {
		return entity == null ? null : read(entity.getId());
	}
	
	/*
	 * Do not create superclass for this. When @Cacheable annotation is located on the method which is overrided in two depth,
	 * the annotation does not work.
	 */
	@Cacheable(CompanyRepository.CACHE_NAME)
	@Override
	public Company read(String id) {
		if (id == null) return null;
		return getTemplate().selectOne(namespace + ".read", id);
	}
		
	@CacheEvict(CompanyRepository.CACHE_NAME)
	@Override
	public void evict(String id) {}

}
