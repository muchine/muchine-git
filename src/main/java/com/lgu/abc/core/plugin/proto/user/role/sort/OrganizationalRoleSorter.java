package com.lgu.abc.core.plugin.proto.user.role.sort;

import java.util.Comparator;

import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRole;
import com.lgu.abc.core.prototype.org.company.Company;

public class OrganizationalRoleSorter implements Comparator<OrganizationalRole> {

	private final Company company;
	
	public OrganizationalRoleSorter(Company company) {
		this.company = company;
	}
	
	@Override
	public int compare(OrganizationalRole o1, OrganizationalRole o2) {
		int compared = o1.module(company).order() - o2.module(company).order();
		if (compared != 0) return compared;
		
		return o1.order() - o2.order();
	}

}
