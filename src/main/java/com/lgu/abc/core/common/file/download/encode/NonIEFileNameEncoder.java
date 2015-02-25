package com.lgu.abc.core.common.file.download.encode;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.common.file.download.encode.exception.UnsupportedFileNameEncodingException;
import com.lgu.abc.core.web.browser.BrowserSpecification;
import com.lgu.abc.core.web.browser.type.IE;

final class NonIEFileNameEncoder implements FileNameEncodable {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public String encode(String filename, BrowserSpecification browser) throws UnsupportedFileNameEncodingException {
		try {
			StringBuilder builder = new StringBuilder();
			
			byte[] fileNameBytes;
			fileNameBytes = filename.getBytes("utf-8");
			
			for (byte b: fileNameBytes) builder.append((char)(b & 0xff));
			
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
			logger.error("can't encode filename: " + filename);
			throw new UnsupportedFileNameEncodingException();
		}
	}

	@Override
	public boolean canSupport(BrowserSpecification browser) {
		return browser == null || !(browser instanceof IE);
	}

}
