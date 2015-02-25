package com.lgu.abc.core.web.view.excel.exception;

import java.util.Locale;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.support.bundle.MessageBundleManager;

@SuppressWarnings("serial")
@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class ExcelImportException extends CoreException {

	private static final String MESSAGE_KEY = "import.message.count";
	
	private final int totalCount;
	
	private final int importedCount;
	
	private final CoreException exception;
	
	public ExcelImportException(CoreException e) {
		this(e, 0, 0);
	}
	
	public ExcelImportException(CoreException e, int total, int imported) {
		super(e);
		
		this.totalCount = total;
		this.importedCount = imported;
		this.exception = e;
	}
	
	public String getCountMessage(Locale locale) {
		return MessageBundleManager.get(MESSAGE_KEY, locale, totalCount, importedCount);
	}

	@Override
	public String getLocalizedMessage(Locale locale) {
		return exception.getLocalizedMessage(locale);
	}
	
}
