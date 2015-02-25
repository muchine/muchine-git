package com.lgu.abc.core.common.interfaces;

import java.util.List;

public interface ContainerAssignable<C extends Containable> {

	void containers(List<C> containables);
	
}
