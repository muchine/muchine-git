package com.lgu.abc.core.prototype.org.company;

import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Protectable;

public class NullCompany extends Company {

	@Override
	public boolean isNull() {
		return true;
	}

	@Override
	public Privilege privilege(Protectable protectable) {
		return null;
	}
	
}
