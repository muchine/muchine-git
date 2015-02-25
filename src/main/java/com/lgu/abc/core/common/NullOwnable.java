package com.lgu.abc.core.common;

import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.interfaces.Ownable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.prototype.org.Party;

public class NullOwnable implements Ownable {

	@Override
	public String getId() {
		return null;
	}

	@Override
	public boolean identical(Identifiable object) {
		return false;
	}

	@Override
	public OwnerType getType() {
		return OwnerType.NULL;
	}

	@Override
	public Name getName() {
		return null;
	}

	@Override
	public ResourceID resourceId() {
		return new ResourceID(NullOwnable.class, getId());
	}

	@Override
	public Privilege authorize(Party party) {
		return null;
	}
	
	@Override
	public int order(String actorId) {
		return 100;
	}
	
}
