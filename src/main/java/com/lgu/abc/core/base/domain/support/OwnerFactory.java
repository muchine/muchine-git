package com.lgu.abc.core.base.domain.support;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.interfaces.Ownable;

public interface OwnerFactory {

	Ownable createOwner(RootEntity entity);
	
}
