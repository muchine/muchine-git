package com.lgu.abc.core.plugin.report.subscription;

import com.lgu.abc.core.base.plugin.ModulePluggable;
import com.lgu.abc.core.prototype.org.company.Company;

public interface OptionalService extends ModulePluggable {

	boolean isUsed(Company company);
	
	String summary(Company company);
	
}
