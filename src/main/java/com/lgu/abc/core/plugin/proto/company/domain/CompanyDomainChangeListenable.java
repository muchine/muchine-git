package com.lgu.abc.core.plugin.proto.company.domain;

import com.lgu.abc.core.prototype.org.company.Company;

public interface CompanyDomainChangeListenable {

	void changed(Company company, String newDomain);
	
}
