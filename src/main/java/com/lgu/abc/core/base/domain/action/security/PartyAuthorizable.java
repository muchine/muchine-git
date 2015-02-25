package com.lgu.abc.core.base.domain.action.security;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.prototype.org.Party;

interface PartyAuthorizable {

	Privilege privilege(RootEntity entity, Party party);
	
	Privilege authorize(RootEntity entity, Party party);
	
}
