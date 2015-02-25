package com.lgu.abc.core.web.view.base.exception;

import java.util.Locale;

import com.lgu.abc.core.exception.CoreException;

@SuppressWarnings("serial")
public class DataImportException extends CoreException {

	public DataImportException() {}
	
	public DataImportException(Throwable e) {
		super(e);
	}

	@Override
	public String getLocalizedMessage(Locale locale) {
		if (getCause() instanceof CoreException) return ((CoreException) getCause()).getLocalizedMessage(locale);
		return super.getLocalizedMessage(locale);
	}
	
}
