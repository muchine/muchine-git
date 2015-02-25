package com.lgu.abc.core.web.view.base.exception;

@SuppressWarnings("serial")
public class TabularDataImportException extends DataImportException {

	private final int imported;
	
	public TabularDataImportException(Throwable e, int imported) {
		super(e);
		this.imported = imported;
	}
	
	public int getImportedCount() {
		return imported;
	}
	
}
