package com.lgu.abc.core.plugin.proto.company.init;

public abstract class AbstractCompanyFinalizer implements CompanyFinalizable {

	protected AbstractCompanyFinalizer(GlobalCompanyFinalizer finalizer) {
		finalizer.add(this);
	}

	@Override
	public int order() {
		return 1;
	}
	
}
