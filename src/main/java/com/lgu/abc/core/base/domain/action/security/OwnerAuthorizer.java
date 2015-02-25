package com.lgu.abc.core.base.domain.action.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.prototype.org.Party;

class OwnerAuthorizer implements PartyAuthorizable {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Privilege privilege(RootEntity entity, Party party) {
		logger.trace("check owner for entity id: " + entity.getId() + ", owner: " + entity.getOwnable());
		return authorize(entity, party);
	}
	
	@Override
	public Privilege authorize(RootEntity entity, Party party) {
		if(!entity.isNull() && entity.getOwnable().identical(party)) 
			return new Privilege(AccessLevel.MANAGE);
		
		return null;
	}
	
}
