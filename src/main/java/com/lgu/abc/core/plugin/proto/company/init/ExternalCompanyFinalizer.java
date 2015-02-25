package com.lgu.abc.core.plugin.proto.company.init;

public abstract class ExternalCompanyFinalizer implements CompanyFinalizable {

	protected ExternalCompanyFinalizer(GlobalExternalCompanyFinalizer finalizer) {
		finalizer.add(this);
	}
			
	@Override
	public int order() {
		return 5;
	}

}
