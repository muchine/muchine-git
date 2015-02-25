package com.lgu.abc.core.base.domain.action.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.prototype.org.Party;

class ResourceAuthorizer implements PartyAuthorizable {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Privilege privilege(RootEntity entity, Party party) {
		return null;
	}
	
	@Override
	public Privilege authorize(RootEntity entity, Party party) {
		return party.role().privilege(entity.resourceId());
	}

}
