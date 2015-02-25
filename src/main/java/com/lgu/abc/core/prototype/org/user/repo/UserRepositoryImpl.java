package com.lgu.abc.core.prototype.org.user.repo;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.lgu.abc.core.prototype.org.user.User;
import com.u2ware.springfield.repository.sqlsession.EntitySqlSessionRepository;

@SuppressWarnings("unchecked")
@Repository
public class UserRepositoryImpl extends EntitySqlSessionRepository<User, String> implements UserRepository {
	
	private final String namespace = User.class.getCanonicalName();
	
	@Autowired
	public UserRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
		super(User.class, sqlSessionTemplate);
	}
	
	@Override
	public User read(User entity) {
		return entity == null ? null : read(entity.getId());
	}

	/*
	 * Do not create superclass for this. When @Cacheable annotation is located on the method which is overrided in two depth,
	 * the annotation does not work.
	 */
	@Cacheable(UserRepository.CACHE_NAME)
	@Override
	public User read(String id) {
		logger.debug("read user... id: " + id);
		
		if (id == null) return null;
		User found = getTemplate().selectOne(namespace + ".read", id);		 
		if (found != null) found.initialize();
		
		return found;
	}

	@Override
	public List<User> findAll(Object query) {
		List<User> found = super.findAll(query);
		if (found != null) {
			for (User user : found) user.initialize();
		}
		
		return found;
	}
	
	@CacheEvict(UserRepository.CACHE_NAME)
	@Override
	public void evict(String id) {}

}
