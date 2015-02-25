package com.lgu.abc.core.base.domain.action;

import java.util.List;

import com.lgu.abc.core.base.domain.support.CompanyOwnerFactory;
import com.lgu.abc.core.common.infra.option.OptionMarshallable;
import com.lgu.abc.core.common.infra.option.company.GenericCompanyOption;
import com.lgu.abc.core.common.interfaces.Ownable;

public class CompanyManagementEntity extends RootEntity implements OptionMarshallable<GenericCompanyOption> {
	
	public CompanyManagementEntity() {
		super(CompanyOwnerFactory.create());
	}
	
	public CompanyManagementEntity(CompanyManagementEntity entity) {
		super(entity);
	}

	@Override
	public Ownable ownable() {
		return getOwnable();
	}

	@Override
	public List<GenericCompanyOption> marshall() {
		return null;
	}

	@Override
	public void unmarshall(Ownable ownable, Iterable<GenericCompanyOption> options) {
		
	}

}
