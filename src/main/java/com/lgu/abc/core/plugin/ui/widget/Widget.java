package com.lgu.abc.core.plugin.ui.widget;

import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.plugin.ui.widget.domain.WidgetInitialization;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetType;

public interface Widget {

	WidgetID id();
	
	Name name();
	
	WidgetType[] types();
		
	WidgetInitialization initializationWithCompany();
	
	WidgetInitialization initializationWithUser();
	
	WidgetInitialization initializationWithWorkspace();
	
}
