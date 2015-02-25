package com.lgu.abc.core.web.view.excel.importer.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.lgu.abc.core.base.transform.importer.RowImportable;
import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.excel.exception.ExcelImportException;
import com.lgu.abc.core.web.view.excel.importer.impl.fixture.MockExceptionRowImportable;
import com.lgu.abc.core.web.view.excel.importer.impl.fixture.MockRowImportable;
import com.lgu.abc.core.web.view.excel.importer.impl.fixture.TestImportedFileFactory;

public class RowExcelImporterTest {

	@Test
	public void testImport() throws Exception {
		int imported = doImport(new MockRowImportable(), 100);
		assertTrue(imported > 1);
	}
	
	@Test
	public void testImportWithMaxIteration() throws Exception {
		int imported = doImport(new MockRowImportable(), 1);
		assertThat(imported, is(1));
	}
	
	@Test
	public void testImportException() throws Exception {
		try {
			doImport(new MockExceptionRowImportable(), 100);
			fail();
		} catch(ExcelImportException e) {
			assertThat(e.getImportedCount(), is(0));
			assertTrue(e.getTotalCount() > 1);
		}
	}
	
	private int doImport(RowImportable importable, int maxIteration) throws Exception {
		// Given
		RowExcelImporter importer = new RowExcelImporter(importable, maxIteration);
		TestImportedFileFactory fileFactory = new TestImportedFileFactory();
		
		// When
		return importer.importExcel(new FileUpload(new User(), fileFactory.generate()));
	}
	
}
