package com.lgu.abc.core.plugin.session.destroy;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.Validate;

public final class GlobalSessionFinalizer implements HttpSessionListener {

	private static Set<SessionFinalizable> finalizables = new HashSet<SessionFinalizable>();
	
	public static synchronized void add(SessionFinalizable finalizable) {
		Validate.notNull(finalizable, "finalizable is null.");
		finalizables.add(finalizable);
	}
	
	@Override
	public final void sessionCreated(HttpSessionEvent session) {
		// Do Nothing
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent session) {
		for (SessionFinalizable finalizable : finalizables) {
			finalizable.finalize(session.getSession());
		}
	}

}
