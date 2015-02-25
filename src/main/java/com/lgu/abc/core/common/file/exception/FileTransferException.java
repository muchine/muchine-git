package com.lgu.abc.core.common.file.exception;

import com.lgu.abc.core.exception.CoreException;

/**
 * A wrapper class of exceptions including IOException, FileNotFoundException, etc occurred during file transfer.   
 * @author Sejoon Lim
 * @since 2014. 2. 5.
 *
 */
@SuppressWarnings("serial")
public class FileTransferException extends CoreException {

	public FileTransferException(Throwable t) {
		super(t);
	}
	
}
