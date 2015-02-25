package com.lgu.abc.core.plugin.proto.user.context.exception;

import java.util.Locale;

import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.support.bundle.MessageBundleManager;

@SuppressWarnings("serial")
public abstract class UserDepartmentChangeException extends CoreException {

	private final boolean recoverable;
	
	protected UserDepartmentChangeException(boolean recoverable) {
		this.recoverable = recoverable;
	}
	
	public String getRecoverableMessage(Locale locale) {
		String recoverableMessage = MessageBundleManager.get(MESSAGE_PREFIX + "recoverable." + getClass().getSimpleName(), locale);
		return recoverable ? recoverableMessage : getLocalizedMessage(locale);
	}
	
}
