package com.lgu.abc.core.base.security.role.company;

import lombok.Getter;

import com.lgu.abc.core.base.security.AbstractRole;

public abstract class CompanyRole extends AbstractRole {

	public static final String MEMBER = "1";
	public static final String ADMIN	= "2";
	
	private final String companyId;
	
	@Getter
	private final String code;
	
	protected CompanyRole(String code, String companyId) {
		super(companyId);
		
		this.companyId = companyId;
		this.code = code;
	}
	
	public static CompanyRole create(String code, String companyId) {
		if (MEMBER.equals(code)) 
			return new CompanyMember(companyId);
		else if (ADMIN.equals(code)) 
			return new CompanyAdministrator(companyId);
		else
			throw new IllegalArgumentException("Invalid code: " + code);
	}
	
	public String companyId() {
		return companyId;
	}
	
}