package com.lgu.abc.core.base.transform.exporter;

import java.util.Locale;

public interface TabularExportable {

	Iterable<String[]> process(Locale locale);
	
}
