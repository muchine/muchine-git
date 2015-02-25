package com.lgu.abc.core.web.view.base.exporter;

import java.util.Locale;
import java.util.Map;

public interface TabularContent {

	Iterable<String[]> content(Map<String, Object> model, Locale locale);
	
	String filename(Locale locale);
	
}
