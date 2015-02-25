package com.lgu.abc.core.plugin.proto.company.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.repo.CompanyRegistry;

@Component
public class CompanyDomainChangePublisher {

	private final Set<CompanyDomainChangeListenable> listenables = new HashSet<CompanyDomainChangeListenable>();
	
	public synchronized void add(CompanyDomainChangeListenable listenable) {
		listenables.add(listenable);
	}
	
	public void publish(Company company, String newDomain) {
		for (CompanyDomainChangeListenable listenable : listenables) {
			listenable.changed(company, newDomain);
		}
		
		CompanyRegistry.evictAllOrganization(company.getId());
	}	
	
}
