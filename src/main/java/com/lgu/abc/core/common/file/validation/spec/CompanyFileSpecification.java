package com.lgu.abc.core.common.file.validation.spec;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.prototype.org.company.Company;

/**
 * A specification describing that a file access should be allowed only to the user who is in the company of the file owner. 
 * @author Sejoon Lim
 * @since 2014. 2. 13.
 *
 */
public class CompanyFileSpecification extends AbstractFileSpecification {

	private final Company company;
	
	CompanyFileSpecification(Company company) {
		Validate.notNull(company, "company is null.");
		this.company = company;
	}
	
	@Override
	protected boolean validate(Collection<File> files) {
		for (File file : files) {
			if (!company.identical(file.getOwner().getUser().getCompany())) return false;
		}
		
		return true;
	}

}
