package com.lgu.abc.core.common.infra.option.company;

import java.io.Serializable;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lgu.abc.core.base.domain.action.CompanyManagementEntity;
import com.lgu.abc.core.base.repository.BaseRepository;
import com.lgu.abc.core.base.repository.RepositoryUtils;
import com.lgu.abc.core.common.infra.option.company.repo.GenericCompanyOptionRepository;
import com.lgu.abc.core.common.infra.option.exception.GenericOptionNotFoundException;
import com.lgu.abc.core.common.interfaces.Ownable;
import com.lgu.abc.core.prototype.org.company.repo.CompanyRegistry;

public abstract class BaseCompanyOptionRepository<T extends CompanyManagementEntity, ID extends Serializable> 
		implements BaseRepository<T, String> {

	@Autowired
	private SqlSessionTemplate template;
	
	@Autowired
	private GenericCompanyOptionRepository repository;
	
	private boolean createInOriginalTable = false;
	
	private boolean updateInOriginalTable = false;
	
	protected BaseCompanyOptionRepository() {}
	
	protected BaseCompanyOptionRepository(boolean createInOriginalTable, boolean updateInOriginalTable) {
		this.createInOriginalTable = createInOriginalTable;
		this.updateInOriginalTable = updateInOriginalTable;
	}
	
	@Override
	public <X> X getTemplate() {
		return repository.getTemplate();
	}

	@Override
	public boolean exists(String id, boolean throwException) {
		return read(id) != null;
	}

	@Override
	public boolean exists(T entity, boolean throwException) {
		return read(entity) != null;
	}

	@Override
	public T create(T entity) {
		if (createInOriginalTable) template.insert(entity.getClass().getCanonicalName() + ".create", entity);
		return save(entity);
	}

	@Override
	public T read(String id) {
		return read(CompanyRegistry.get(id));
	}

	@Override
	public T read(T entity) {
		T found = read(entity.ownable());
		RepositoryUtils.injectActionContext(entity, found);
		
		return found;
	}
	
	private T read(Ownable ownable) {
		GenericCompanyOption query = new GenericCompanyOption();
		query.setOwnable(ownable);
				
		List<GenericCompanyOption> found = repository.findAll(query);
		T instance = newInstance();
		
		try {			
			instance.unmarshall(ownable, found);
			instance.setId(ownable.getId());	
		} catch (GenericOptionNotFoundException e) {
			return null;
		}
		
		return instance;
	}

	@Override
	public T update(T entity) {
		if (updateInOriginalTable) template.update(entity.getClass().getCanonicalName() + ".update", entity);
		return save(entity);
	}

	@Override
	public void delete(T entity) {
		List<GenericCompanyOption> options = entity.marshall();
		for (GenericCompanyOption option : options) {
			repository.delete(option);
		}
	}

	@Override
	public T createOrUpdate(T entity) {
		return save(entity);
	}

	@Override
	public List<T> findAll(Object query) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> findAll(Object query, Sort sort) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Page<T> findAll(Object query, Pageable pageable) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long count(Object query) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T save(T entity) {
		List<GenericCompanyOption> options = entity.marshall();
		for (GenericCompanyOption option : options) {
			repository.save(option);
		}
		
		return entity;
	}

	@Override
	public void deleteAll(Object query) {
		repository.deleteAll(query);
	}

	@Override
	public void rearrange(T entity, int to) {
		throw new UnsupportedOperationException();
	}
	
	protected abstract T newInstance();

}
