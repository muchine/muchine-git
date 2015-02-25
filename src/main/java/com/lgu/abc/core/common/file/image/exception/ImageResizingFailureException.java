package com.lgu.abc.core.common.file.image.exception;

import com.lgu.abc.core.exception.CoreException;

@SuppressWarnings("serial")
public class ImageResizingFailureException extends CoreException {

	public ImageResizingFailureException() {}
	
	public ImageResizingFailureException(Throwable e) {
		super(e);
	}
	
}
