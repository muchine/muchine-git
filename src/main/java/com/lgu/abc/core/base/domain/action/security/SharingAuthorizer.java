package com.lgu.abc.core.base.domain.action.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.action.ShareableEntity;
import com.lgu.abc.core.common.infra.share.domain.Accessor;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.prototype.org.Party;

class SharingAuthorizer implements PartyAuthorizable {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Privilege privilege(RootEntity entity, Party party) {
		return null;
	}
	
	@Override
	public Privilege authorize(RootEntity entity, Party party) {
		if (!(entity instanceof ShareableEntity)) return null;
		
		ShareableEntity casted = (ShareableEntity) entity;
		
		logger.trace("accessors: " + casted.getAccessors());
		if (casted.getAccessors() == null || casted.getAccessors().isEmpty()) return null;
		
		return authorizeAllPartyHierarchy(casted, party);
	}
	
	private Privilege authorizeAllPartyHierarchy(ShareableEntity entity, Party party) {
		for (Party item = party; item != null; item = item.upper()) {
			Privilege privilege = authorizeParty(entity, item);
			if (privilege != null) return privilege;
		}
		
		return null;
	}
	
	private Privilege authorizeParty(ShareableEntity entity, Party party) {
		for (Accessor accessor : entity.getAccessors()) {
			if (accessor.hasParty(party)) return accessor.privilege();
		}
		
		return null;
	}
	
}
