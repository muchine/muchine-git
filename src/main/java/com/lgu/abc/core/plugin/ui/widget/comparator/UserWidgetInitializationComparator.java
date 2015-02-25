package com.lgu.abc.core.plugin.ui.widget.comparator;

import java.util.Comparator;

import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetCategory;

public class UserWidgetInitializationComparator implements Comparator<Widget> {

	private final WidgetCategory category;
	
	public UserWidgetInitializationComparator(WidgetCategory category) {
		this.category = category;
	}
	
	@Override
	public int compare(Widget o1, Widget o2) {
		return o1.initializationWithUser().order(category) - o2.initializationWithUser().order(category);
	}

}
