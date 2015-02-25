package com.lgu.abc.core.base.domain.support;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.interfaces.Ownable;

public class UserOwnerFactory implements OwnerFactory {

	private static final UserOwnerFactory instance = new UserOwnerFactory();
	
	private UserOwnerFactory() {}
	
	@Override
	public Ownable createOwner(RootEntity entity) {
		return entity.getActor();
	}
	
	public static OwnerFactory create() {
		return instance;
	}

}
