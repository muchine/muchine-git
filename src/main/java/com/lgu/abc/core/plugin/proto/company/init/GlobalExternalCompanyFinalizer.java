package com.lgu.abc.core.plugin.proto.company.init;

import java.util.HashSet;
import java.util.Set;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class GlobalExternalCompanyFinalizer implements CompanyFinalizable {

	private Set<CompanyFinalizable> finalizables = new HashSet<CompanyFinalizable>();
	
	@Setter
	private boolean enabled = true;
	
	@Autowired
	private GlobalExternalCompanyFinalizer(GlobalCompanyFinalizer finalizer) {
		finalizer.add(this);
	}
	
	public synchronized void add(ExternalCompanyFinalizer finalizable) {
		finalizables.add(finalizable);
	}
	
	@Override
	public void finalize(Company company) {
		if (!enabled) return;
		
		for (CompanyFinalizable finalizable : finalizables) {
			finalizable.finalize(company);
		}
	}
	
	@Override
	public int order() {
		return 5;
	}

}
