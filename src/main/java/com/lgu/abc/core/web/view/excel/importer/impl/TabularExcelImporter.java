package com.lgu.abc.core.web.view.excel.importer.impl;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;

import com.lgu.abc.core.base.transform.importer.TabularImportable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.base.exception.TabularDataImportException;
import com.lgu.abc.core.web.view.excel.exception.ExcelImportException;
import com.lgu.abc.core.web.view.excel.importer.AbstractExcelImporter;

public class TabularExcelImporter extends AbstractExcelImporter {

	private static final int MAX_ITERATION_NUMBER = 1000;
	
	private final TabularImportable importable;
	
	private final int cellSize;
	
	public TabularExcelImporter(TabularImportable importable, int cellSize) {
		this.importable = importable;
		this.cellSize = cellSize;
	}
	
	@Override
	protected int process(User actor, Iterator<Row> iterator) throws ExcelImportException {
		TabularListRowExcelImporter rowImporter = new TabularListRowExcelImporter(cellSize);
		RowExcelImporter excelImporter = new RowExcelImporter(rowImporter, MAX_ITERATION_NUMBER);
		 
		excelImporter.process(actor, iterator);
		
		try {
			return importable.process(actor, rowImporter.data());
		} catch (TabularDataImportException e) {
			throw new ExcelImportException(e, rowImporter.count(), e.getImportedCount());
		}
	}
	
}
