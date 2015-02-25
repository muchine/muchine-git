package com.lgu.abc.core.plugin.proto.company.init;

import com.lgu.abc.core.prototype.org.company.Company;

public abstract class AbstractCompanyInitializer implements CompanyInitializable {

	protected AbstractCompanyInitializer(GlobalCompanyInitializer initializer) {
		initializer.add(this);
	}
	
	@Override
	public void finalize(Company company) {

	}

	@Override
	public int order() {
		return 2;
	}

}
