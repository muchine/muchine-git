package com.lgu.abc.core.exception.common;

import com.lgu.abc.core.exception.CoreException;

@SuppressWarnings("serial")
public class IllegalRequestException extends CoreException {

	private static final int HTTP_CODE = 600;
	
	private final String bundleKey;
	
	public IllegalRequestException() {
		this.bundleKey = super.getBundleKey();
	}
	
	public IllegalRequestException(CoreException e) {
		this.bundleKey = e.getBundleKey();
	}
	
	public IllegalRequestException(String bundleKey) {
		this.bundleKey = bundleKey;
	}
	
	@Override
	public String getBundleKey() {
		return bundleKey;
	}

	public int getHttpCode() {
		return HTTP_CODE;
	}
	
}
