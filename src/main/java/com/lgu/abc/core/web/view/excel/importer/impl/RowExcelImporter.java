package com.lgu.abc.core.web.view.excel.importer.impl;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.lgu.abc.core.base.transform.importer.RowImportable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.base.exception.DataImportException;
import com.lgu.abc.core.web.view.excel.exception.ExcelImportException;
import com.lgu.abc.core.web.view.excel.exception.InvalidCellFormatException;
import com.lgu.abc.core.web.view.excel.importer.AbstractExcelImporter;

public class RowExcelImporter extends AbstractExcelImporter {

	private final RowImportable importable;
	
	private final int maxIteration;
		
	private int total = 0;
	
	private int imported = 0;
	
	public RowExcelImporter(RowImportable importable, int maxIteration) {
		this.importable = importable;
		this.maxIteration = maxIteration;
	}

	@Override
	protected int process(User actor, Iterator<Row> iterator) throws ExcelImportException {
		skipHeader(iterator);
		
		DataImportException exception = null;
		
		for (int i = 0; i < maxIteration; i++) {
			if (!iterator.hasNext()) break;
			
			Row row = iterator.next();
			logger.debug("processing " + row.getRowNum() + "th row...");
			if (isFirstCellEmpty(row)) break;
			
			total++;
			try {
				if (exception != null) continue;
				if (importable.process(actor, createCells(row))) imported++;
			} catch(DataImportException e) {
				exception = e;
			}
		}
		
		if (exception != null) throw new ExcelImportException(exception, total, imported);
		return imported;
	}
	
	private void skipHeader(Iterator<Row> iterator) {
		if (iterator.hasNext()) iterator.next();
	}
	
	private boolean isFirstCellEmpty(Row row) {
		return row.getFirstCellNum() != 0;
	}
	
	private String[] createCells(Row row) {
		String[] cells = new String[importable.getCellSize()];
		initializeCells(cells);
		
		for (Cell cell : row) {
			int index = cell.getColumnIndex();
			logger.debug("cell[" + index + "]: " + cell);
			
			cells[index++] = getCellValue(cell);
			if (index >= cells.length) break;
		}
		
		return cells;
	}
	
	private String getCellValue(Cell cell) {
		switch(cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC : 
				return String.valueOf((long) cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING :
			case Cell.CELL_TYPE_FORMULA :
			case Cell.CELL_TYPE_BLANK : 
				return cell.getStringCellValue();
			default: 
				throw new InvalidCellFormatException();
		}
	}
	
	private void initializeCells(String[] cells) {
		for (int i = 0, n = cells.length; i < n; i++) {
			cells[i] = "";
		}
	}
	
}
