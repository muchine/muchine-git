package com.lgu.abc.core.common.interfaces;

import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.prototype.org.Party;

public interface Containable extends Identifiable {

	ResourceID resourceId();
	
	Privilege authorize(Party party);
	
}
