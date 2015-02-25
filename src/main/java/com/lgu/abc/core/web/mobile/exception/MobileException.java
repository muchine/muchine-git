package com.lgu.abc.core.web.mobile.exception;

import java.util.Locale;

import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.web.mobile.MobileActionResult;
import com.lgu.abc.core.web.mobile.MobileView;

@SuppressWarnings("serial")
public abstract class MobileException extends CoreException {

	private static String MESSAGE_PREFIX = "exception.mobile.message.";
	
	private final String code;
	
	protected MobileException(String code) {
		this.code = code;
	}
	
	public MobileView getView(Locale locale) {
		return new MobileView(new MobileActionResult(code, getLocalizedMessage(locale)));
	}

	@Override
	public String getBundleKey() {
		return MESSAGE_PREFIX + getClass().getSimpleName();
	}
	
}
