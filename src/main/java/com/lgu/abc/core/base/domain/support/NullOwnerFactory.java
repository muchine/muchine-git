package com.lgu.abc.core.base.domain.support;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.NullOwnable;
import com.lgu.abc.core.common.interfaces.Ownable;

public class NullOwnerFactory implements OwnerFactory {

	private static final NullOwnerFactory instance = new NullOwnerFactory();
	
	private NullOwnerFactory() {}
	
	@Override
	public Ownable createOwner(RootEntity entity) {
		return new NullOwnable();
	}
	
	public static OwnerFactory create() {
		return instance;
	}

}
