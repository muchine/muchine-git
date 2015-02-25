package com.lgu.abc.core.common.query;

import lombok.Data;

import org.apache.commons.lang.Validate;

public @Data class Searcher {
	
	private final String text;
	
	public Searcher(String text) {
		Validate.notNull(text, "search text is null.");
		this.text = text;
	}
	
}
