package com.lgu.abc.core.common.infra.option.company;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.common.infra.option.GenericOption;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class GenericCompanyOption extends GenericOption {
	
	public GenericCompanyOption() {}
	
	public GenericCompanyOption(User actor, String name, String value) {
		this(actor.getCompany(), name, value);
		setActor(actor);
	}
	
	public GenericCompanyOption(Company company, String name, String value) {
		super(company, name, value);
	}
		
}
