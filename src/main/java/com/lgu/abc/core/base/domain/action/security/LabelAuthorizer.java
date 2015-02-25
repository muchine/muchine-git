package com.lgu.abc.core.base.domain.action.security;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.domain.label.Label;
import com.lgu.abc.core.common.domain.label.interfaces.LabelAttachable;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.prototype.org.Party;

class LabelAuthorizer implements PartyAuthorizable {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Privilege privilege(RootEntity entity, Party party) {
		if (!(entity instanceof LabelAttachable)) return null;
		
		Collection<? extends Label> labels = ((LabelAttachable<?>) entity).labels();
		if (CollectionUtils.isEmpty(labels)) return null;
		
		logger.trace("labels: " + labels);
		Privilege result = PrivilegePolicy.authorize(party, labels);
		
		/*
		 * If a privilege cannot be determined even though labels mapped with an entity exists, the party can't get access to it.
		 */
		return result != null ? result : new Privilege(AccessLevel.NONE);
	}

	@Override
	public Privilege authorize(RootEntity entity, Party party) {
		return null;
	}

}
