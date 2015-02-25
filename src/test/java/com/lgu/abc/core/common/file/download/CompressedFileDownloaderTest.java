package com.lgu.abc.core.common.file.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.lgu.abc.core.common.file.transfer.ByteArrayTransference;
import com.lgu.abc.core.common.file.transfer.FileTransferable;
import com.lgu.abc.core.web.browser.UserAgents;
import com.lgu.abc.test.mock.MockFileGenerator;

public class CompressedFileDownloaderTest {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String filename = "한글 Zip 파일";
	
	private final MockFileGenerator generator = new MockFileGenerator();
		
	@Test
	public void testDownload() throws Exception {
		// Given
		Collection<FileTransferable> transferables = prepareFileTransferables();
		
		FileDownloadable downloader = new CompressedFileDownloader(filename, transferables);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		UserAgents.setUserAgent(UserAgents.CHROME, request);
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		// When
		downloader.download(request, response);
		
		// Then
		FileDownloadTestAsserter asserter = new FileDownloadTestAsserter(filename);
		asserter.assertContentLength(response);
		asserter.assertFileName(request, response);
//		saveFile(response.getContentAsByteArray());
	}
	
	private Collection<FileTransferable> prepareFileTransferables() throws IOException {
		Collection<FileTransferable> transferables = new ArrayList<FileTransferable>();
		
		InputStream in = generator.getInputStreamFrom(MockFileGenerator.KOREAN_FILE1_NAME);
		transferables.add(new ByteArrayTransference(MockFileGenerator.KOREAN_FILE1_NAME, in));
		
		in = generator.getInputStreamFrom(MockFileGenerator.KOREAN_FILE2_NAME);
		transferables.add(new ByteArrayTransference(MockFileGenerator.KOREAN_FILE2_NAME, in));
		
		return transferables;
	}
	
	@SuppressWarnings("unused")
	private void saveFile(byte[] content) {
		File file = new File("D:\\test-downloaded.zip");
		
		FileOutputStream out = null;
		try {
			if (!file.exists()) file.createNewFile();
			
			out = new FileOutputStream(file);
			out.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) try { out.close(); } catch (IOException e) {}
		}		
	}
	
}
