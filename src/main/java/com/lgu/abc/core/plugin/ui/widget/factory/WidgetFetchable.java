package com.lgu.abc.core.plugin.ui.widget.factory;

import java.util.List;

import com.lgu.abc.core.plugin.ui.widget.Widget;

public interface WidgetFetchable {

	List<Widget> fetch(WidgetFactory factory);
	
}
