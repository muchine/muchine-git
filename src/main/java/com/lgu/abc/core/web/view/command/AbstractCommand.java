package com.lgu.abc.core.web.view.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.interfaces.Command;

public abstract class AbstractCommand implements Command {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public RootEntity create() {
		throw new UnsupportedOperationException();
	}

	@Override
	public RootEntity update(RootEntity retrieved) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RootEntity delete(RootEntity retrieved) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RootEntity read() {
		throw new UnsupportedOperationException();
	}

}
