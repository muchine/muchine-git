package com.lgu.abc.core.base.security.role.company;

import java.util.ArrayList;
import java.util.Collection;

import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.Permission;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.prototype.org.company.Company;

public class CompanyMember extends CompanyRole {
	
	public CompanyMember(String companyId) {
		super(CompanyRole.MEMBER, companyId);
	}
	
	@Override
	protected Collection<Permissible> initialize(String companyId) {
		Collection<Permissible> permissibles = new ArrayList<Permissible>();
		
		ResourceID resource = new ResourceID(Company.class, companyId);
		Permission permission = new Permission(resource, new Privilege(AccessLevel.READ));
		
		permissibles.add(permission);
		return permissibles;
	}

}
