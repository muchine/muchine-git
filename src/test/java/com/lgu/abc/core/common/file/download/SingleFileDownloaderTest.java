package com.lgu.abc.core.common.file.download;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.lgu.abc.core.common.file.transfer.ByteArrayTransference;
import com.lgu.abc.core.common.file.transfer.FileTransferable;
import com.lgu.abc.core.web.browser.UserAgents;
import com.lgu.abc.test.mock.MockFileGenerator;

public class SingleFileDownloaderTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final MockFileGenerator generator = new MockFileGenerator();
	
	@Test
	public void testDownload() throws Exception {
		// Given
		String filename = MockFileGenerator.KOREAN_FILE1_NAME;
		
		InputStream in = generator.getInputStreamFrom(filename);
		FileTransferable transferable = new ByteArrayTransference(filename, in);
		FileDownloadable downloader = new SingleFileDownloader(transferable);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		UserAgents.setUserAgent(UserAgents.CHROME, request);
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		// When
		downloader.download(request, response);
		
		// Then
		FileDownloadTestAsserter asserter = new FileDownloadTestAsserter(filename);
		asserter.assertContentLength(response);
		asserter.assertFileName(request, response);
	}
	
}
