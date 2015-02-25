package com.lgu.abc.core.web.view.condition;

import lombok.Data;

public @Data class ConditionItem {

	private final String code;
	
	private final String name;
	
	private final boolean selected;
	
	public ConditionItem(String code, String name, boolean selected) {
		this.code = code;
		this.name = name;
		this.selected =selected;
	}
	
}
