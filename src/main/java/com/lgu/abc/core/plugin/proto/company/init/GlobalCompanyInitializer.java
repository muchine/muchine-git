package com.lgu.abc.core.plugin.proto.company.init;

import java.util.HashSet;
import java.util.Set;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;

@Component
public class GlobalCompanyInitializer implements CompanyInitializable {

	private Set<CompanyInitializable> initializables = new HashSet<CompanyInitializable>();
	
	/*
	 * This is for unit test. Do not set enabled to false in production environment 
	 */
	@Setter
	private boolean enabled = true;
	
	@Autowired
	private GlobalCompanyInitializer(GlobalCompanyFinalizer finalizer) {
		finalizer.add(this);
	}
	
	public synchronized void add(CompanyInitializable initializable) {
		initializables.add(initializable);
	}
	
	@Override
	public void initialize(User user, Company company) {
		if (!enabled) return;
		
		for (CompanyInitializable initializable : initializables) {
			initializable.initialize(user, company);
		}	
	}

	@Override
	public void finalize(Company company) {
		if (!enabled) return;
		
		for (CompanyFinalizable finalizable : initializables) {
			finalizable.finalize(company);
		}
	}

	@Override
	public int order() {
		return 2;
	}

}
