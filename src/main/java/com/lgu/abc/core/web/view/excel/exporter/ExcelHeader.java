package com.lgu.abc.core.web.view.excel.exporter;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.lgu.abc.core.web.view.base.exporter.TabularHeader;

public interface ExcelHeader extends TabularHeader {

	void style(CellStyle style);
	
	void font(Font font);
	
}
