package com.lgu.abc.core.plugin.ui.widget.comparator;

import java.util.Comparator;

import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetCategory;

public class CompanyWidgetInitializationComparator implements Comparator<Widget> {
	
	private static final CompanyWidgetInitializationComparator instance = new CompanyWidgetInitializationComparator();

	@Override
	public int compare(Widget o1, Widget o2) {
		return o1.initializationWithCompany().order(WidgetCategory.main) - o2.initializationWithCompany().order(WidgetCategory.main);
	}
	
	public static CompanyWidgetInitializationComparator instance() {
		return instance;
	}

}
