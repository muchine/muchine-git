package com.lgu.abc.core.mybatis.handlers.query.interfaces;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.prototype.org.Party;

public class ManageableQuery implements Queryable {

	private Party party;
	
	@Override
	public void setParty(Party party) {
		this.party = party;
	}

	@Override
	public boolean canQuery(Containable containable) {
		Validate.notNull(party, "party is null. set party first.");
		
		Privilege priv = party.privilege(containable);
		return priv != null && priv.isManage();
	}

}
