package com.lgu.abc.core.prototype.ui.widget.domain;

import com.lgu.abc.core.prototype.ui.widget.position.ThreeColumnsVerticalWidgetDisposer;
import com.lgu.abc.core.prototype.ui.widget.position.TwoColumnsVerticalWidgetDisposer;
import com.lgu.abc.core.prototype.ui.widget.position.WidgetDisposer;

public enum WidgetCategory {

	/*
	 * TODO Replace enums with upper case. The lower case is not recommended. 
	 */
	main(new ThreeColumnsVerticalWidgetDisposer()), 
	mywork(new TwoColumnsVerticalWidgetDisposer()),
	workspace(new TwoColumnsVerticalWidgetDisposer());
	
	private final WidgetDisposer disposer;
	
	private WidgetCategory(WidgetDisposer disposer) {
		this.disposer = disposer;
	}
	
	public WidgetDisposer disposer() {
		return disposer;
	}
	
}
