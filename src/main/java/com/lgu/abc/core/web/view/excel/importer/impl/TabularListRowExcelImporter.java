package com.lgu.abc.core.web.view.excel.importer.impl;

import java.util.ArrayList;
import java.util.List;

import com.lgu.abc.core.base.transform.importer.RowImportable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.base.exception.DataImportException;

public class TabularListRowExcelImporter implements RowImportable {
	
	private final List<String[]> data = new ArrayList<String[]>();
	
	private final int cellSize;
	
	TabularListRowExcelImporter(int cellSize) {
		this.cellSize = cellSize;
	}

	@Override
	public boolean process(User actor, String[] row) throws DataImportException {
		data.add(row);
		return true;
	}

	@Override
	public int getCellSize() {
		return cellSize;
	}
	
	public List<String[]> data() {
		return data;
	}
	
	public int count() {
		return data.size();
	}

}
