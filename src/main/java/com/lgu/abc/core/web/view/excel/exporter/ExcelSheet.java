package com.lgu.abc.core.web.view.excel.exporter;

import java.util.Locale;

import com.lgu.abc.core.web.view.excel.exporter.style.SheetStyle;

public interface ExcelSheet {
	
	String name(Locale locale);

	SheetStyle style();
		
}
