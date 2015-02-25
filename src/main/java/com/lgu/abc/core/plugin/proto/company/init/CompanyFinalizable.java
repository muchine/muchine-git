package com.lgu.abc.core.plugin.proto.company.init;

import com.lgu.abc.core.base.plugin.Sortable;
import com.lgu.abc.core.prototype.org.company.Company;

public interface CompanyFinalizable extends Sortable {

	void finalize(Company company);
	
}
