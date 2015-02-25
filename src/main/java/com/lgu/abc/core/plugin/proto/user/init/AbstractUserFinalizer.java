package com.lgu.abc.core.plugin.proto.user.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractUserFinalizer implements UserFinalizable {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected AbstractUserFinalizer(GlobalUserFinalizer finalizer) {
		finalizer.add(this);
	}
	
}
