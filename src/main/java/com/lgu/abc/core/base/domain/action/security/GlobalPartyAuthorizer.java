package com.lgu.abc.core.base.domain.action.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.prototype.org.Party;

public class GlobalPartyAuthorizer {
	
	private static final Log logger = LogFactory.getLog(GlobalPartyAuthorizer.class);
	
	private static List<PartyAuthorizable> authorizables = new ArrayList<PartyAuthorizable>();
	
	static {
		authorizables.add(new OwnerAuthorizer());
		authorizables.add(new LabelAuthorizer());
		authorizables.add(new SharingAuthorizer());
		authorizables.add(new ResourceAuthorizer());
		authorizables.add(new ContainerAuthorizer());
	}
	
	public static Privilege privilege(RootEntity entity, Party party) {
		for (PartyAuthorizable authorizable : authorizables) {
			Privilege privilege = authorizable.privilege(entity, party);
			if (privilege != null) {
				logger.trace("privilege found: " + privilege);
				return privilege;
			}
		}
		
		return null;
	}
	
	public static Privilege authorize(RootEntity entity, Party party) {
		for (PartyAuthorizable authorizable : authorizables) {
			Privilege privilege = authorizable.authorize(entity, party);
			if (privilege != null) {
				logger.trace("privilege found: " + privilege);
				return privilege;
			}
		}
		
		return null;
	}

}
