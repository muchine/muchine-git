package com.lgu.abc.core.plugin.ui.widget.view;

import lombok.Data;

import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.ui.widget.WidgetOption;

public @Data class WidgetOptionView {

	private final WidgetOption option;
	
	private final User actor;
	
	private final Widget widget;
	
	public WidgetOptionView(WidgetOption option, User actor, Widget widget) {
		this.option = option;
		this.actor = actor;
		this.widget = widget;
	}
	
}
