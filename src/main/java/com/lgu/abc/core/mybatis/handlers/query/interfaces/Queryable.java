package com.lgu.abc.core.mybatis.handlers.query.interfaces;

import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.prototype.org.Party;

public interface Queryable {

	void setParty(Party party);
	
	boolean canQuery(Containable containable);
	
}
