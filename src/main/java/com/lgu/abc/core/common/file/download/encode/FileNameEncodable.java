package com.lgu.abc.core.common.file.download.encode;

import com.lgu.abc.core.common.file.download.encode.exception.UnsupportedFileNameEncodingException;
import com.lgu.abc.core.web.browser.BrowserSpecification;

public interface FileNameEncodable {

	String encode(String filename, BrowserSpecification browser) throws UnsupportedFileNameEncodingException;

	boolean canSupport(BrowserSpecification browser);
	
}
