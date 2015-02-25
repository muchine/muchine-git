package com.lgu.abc.core.web.view.excel.exporter.style;

import lombok.Data;

public @Data class SheetStyle {
	
	private boolean displayGridLines = false;
	
	private boolean printGridLines = false;
	
	private boolean fitToPage = true;
	
	private boolean horizontallyCenter = true;
	
	private boolean autobreaks = true;
	
	private PrintStyle printStyle = new PrintStyle();
	
}
