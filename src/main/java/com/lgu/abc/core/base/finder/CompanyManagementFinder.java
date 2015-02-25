package com.lgu.abc.core.base.finder;

import com.lgu.abc.core.base.domain.action.CompanyManagementEntity;
import com.lgu.abc.core.prototype.org.user.User;
import com.u2ware.springfield.service.EntityService;

public abstract class CompanyManagementFinder<T extends CompanyManagementEntity> {

	private final EntityService<T, T> service;
	
	protected CompanyManagementFinder(EntityService<T, T> service) {
		this.service = service;
	}
	
	public T findByCompanyId(String companyId) {
		T entity = create();
		entity.setId(companyId);
		
		return service.read(entity);
	}
	
	public T findByUser(User user) {
		T entity = create();
		entity.setActor(user);
		
		return service.read(entity);
	}	
	
	protected abstract T create();
	
	
}
