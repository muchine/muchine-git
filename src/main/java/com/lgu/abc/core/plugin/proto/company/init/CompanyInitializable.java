package com.lgu.abc.core.plugin.proto.company.init;

import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;

public interface CompanyInitializable extends CompanyFinalizable {

	void initialize(User user, Company company);
	
}
