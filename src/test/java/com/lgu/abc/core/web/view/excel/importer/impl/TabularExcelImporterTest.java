package com.lgu.abc.core.web.view.excel.importer.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.lgu.abc.core.base.transform.importer.TabularImportable;
import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.excel.exception.ExcelImportException;
import com.lgu.abc.core.web.view.excel.importer.impl.fixture.MockExceptionTabularImportable;
import com.lgu.abc.core.web.view.excel.importer.impl.fixture.TestImportedFileFactory;

public class TabularExcelImporterTest {

	@Test
	public void testImportException() throws Exception {
		try {
			doImport(new MockExceptionTabularImportable(1), 24);
			fail();
		} catch(ExcelImportException e) {
			assertThat(e.getImportedCount(), is(1));
			assertTrue(e.getTotalCount() > 1);
		}
	}
	
	private int doImport(TabularImportable importable, int cellSize) throws Exception {
		// Given
		TabularExcelImporter importer = new TabularExcelImporter(importable, cellSize);
		TestImportedFileFactory fileFactory = new TestImportedFileFactory();
		
		// When
		return importer.importExcel(new FileUpload(new User(), fileFactory.generate()));
	}
	
}
