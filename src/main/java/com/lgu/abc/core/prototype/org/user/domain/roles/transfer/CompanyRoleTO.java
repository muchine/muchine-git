package com.lgu.abc.core.prototype.org.user.domain.roles.transfer;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.role.company.CompanyRole;
import com.lgu.abc.core.common.infra.log.Logger;
import com.lgu.abc.core.prototype.org.user.domain.roles.Role;

public @Data class CompanyRoleTO {
	
	@JsonIgnore
	protected final Logger logger = new Logger(getClass());
	
	private String companyId;
	
	private String companyRoleCode;
		
	public CompanyRoleTO() {}
	
	public CompanyRoleTO(Role role) {
		for (Permissible permissible : role) {
			initialize(permissible);
		}
	}
		
	public List<Permissible> permissibles() {
		List<Permissible> roles = new ArrayList<Permissible>();
		roles.add(CompanyRole.create(companyRoleCode, companyId));
		
		return roles;
	}
	
	private void initialize(Permissible role) {
		if (!(role instanceof CompanyRole)) return;
		companyRoleCode = ((CompanyRole) role).getCode();
	}
	
}
