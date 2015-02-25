package com.lgu.abc.core.common.file.download;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.lgu.abc.core.common.file.download.encode.FileNameEncoder;
import com.lgu.abc.core.web.support.WebUtils;

public class FileDownloadTestAsserter {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String filename;
	
	public FileDownloadTestAsserter(String filename) {
		this.filename = filename;
	}
	
	public void assertContentLength(MockHttpServletResponse response) {
		int contentLength = response.getContentAsByteArray().length;
		logger.debug("content length: " + contentLength);
		assertTrue(contentLength > 0);
	}
	
	public void assertFileName(MockHttpServletRequest request, MockHttpServletResponse response) {
		String disposition = response.getHeader("Content-Disposition");
		logger.debug("disposition: " + disposition);
		assertThat(disposition.contains(getEncodedFilename(request)), is(true));
	}
	
	private String getEncodedFilename(HttpServletRequest request) {
		FileNameEncoder encoder = new FileNameEncoder();
		String encoded = encoder.encode(filename, WebUtils.browser(request));
		
		logger.debug("encoded file name: " + encoded);
		return encoded;
	}
	
}
