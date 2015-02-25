package com.lgu.abc.core.plugin.ui.widget.selector;

import com.lgu.abc.core.plugin.ui.widget.Widget;

public class AnyWidgetSelectable implements WidgetSelectable {

	private static AnyWidgetSelectable instance = new AnyWidgetSelectable();
	
	private AnyWidgetSelectable() {}
	
	@Override
	public boolean canSelect(Widget widget) {
		return true;
	}
	
	public static AnyWidgetSelectable instance() {
		return instance;
	}

}
