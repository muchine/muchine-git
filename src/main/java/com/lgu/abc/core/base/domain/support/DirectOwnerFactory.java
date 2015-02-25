package com.lgu.abc.core.base.domain.support;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.interfaces.Ownable;

public class DirectOwnerFactory implements OwnerFactory {

	private final Ownable ownable;
	
	public DirectOwnerFactory(Ownable ownable) {
		this.ownable = ownable;
	}
	
	@Override
	public Ownable createOwner(RootEntity entity) {
		return ownable;
	}

}
