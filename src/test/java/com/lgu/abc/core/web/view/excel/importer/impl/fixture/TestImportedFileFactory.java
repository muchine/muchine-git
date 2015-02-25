package com.lgu.abc.core.web.view.excel.importer.impl.fixture;

import org.springframework.mock.web.MockMultipartFile;

public class TestImportedFileFactory {

	public static final String MULTIPART_FILENAME = "upload";

	public MockMultipartFile generate() throws Exception {
		return new MockMultipartFile(MULTIPART_FILENAME, getClass().getResourceAsStream("/files/excel-import-test.xls"));
	}
	
}
