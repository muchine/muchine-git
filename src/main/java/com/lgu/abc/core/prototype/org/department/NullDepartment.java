package com.lgu.abc.core.prototype.org.department;

import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Protectable;

public class NullDepartment extends Department {
	
	private static final NullDepartment instance = new NullDepartment();
	
	private NullDepartment() {
		setId(Department.NULL_DEPARTMENT);
	}
	
	@Override
	public Privilege privilege(Protectable protectable) {
		return null;
	}
	
	public static NullDepartment instance() {
		return instance;
	}
	
}
