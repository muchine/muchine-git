package com.lgu.abc.core.common.interfaces;

import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.prototype.org.Party;

public interface Protectable {

	public Privilege privilege(Party party);
	
}
