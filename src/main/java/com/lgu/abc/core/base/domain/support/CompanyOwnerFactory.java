package com.lgu.abc.core.base.domain.support;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.interfaces.Ownable;

public class CompanyOwnerFactory implements OwnerFactory {

	private static final CompanyOwnerFactory instance = new CompanyOwnerFactory();
	
	private CompanyOwnerFactory() {}
	
	@Override
	public Ownable createOwner(RootEntity entity) {
		return entity.getActor().getCompany();
	}
	
	public static OwnerFactory create() {
		return instance;
	}

}
