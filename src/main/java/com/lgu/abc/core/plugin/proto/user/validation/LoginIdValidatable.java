package com.lgu.abc.core.plugin.proto.user.validation;

import com.lgu.abc.core.prototype.org.company.Company;

public interface LoginIdValidatable {

	void validate(String loginId, Company company) throws LoginIdValidationException;
	
}
