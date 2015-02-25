package com.lgu.abc.core.plugin.ui.widget.domain;

public class NullWidgetInitialization {

	private static final WidgetInitialization instance = new WidgetInitialization(false, null);
	
	public static WidgetInitialization instance() {
		return instance;
	}
	
}
