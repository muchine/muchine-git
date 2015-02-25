package com.lgu.abc.core.plugin.proto.user.validation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class GlobalLoginIdValidator {

	private final Set<LoginIdValidatable> validators = new HashSet<LoginIdValidatable>();
	
	public synchronized void add(LoginIdValidatable validator) {
		validators.add(validator);
	}
	
	public void validate(String loginId, Company company) {
		for (LoginIdValidatable validator : validators) {
			validator.validate(loginId, company);
		}
	}
	
}
