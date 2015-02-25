package com.lgu.abc.core.common.interfaces;

import com.lgu.abc.core.base.domain.action.RootEntity;

public interface Command {

	RootEntity create();
	
	RootEntity update(RootEntity retrieved);
	
	RootEntity delete(RootEntity retrieved);
	
	RootEntity read();
	
}
