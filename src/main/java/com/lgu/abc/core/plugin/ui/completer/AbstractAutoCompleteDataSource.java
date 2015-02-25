package com.lgu.abc.core.plugin.ui.completer;

public abstract class AbstractAutoCompleteDataSource implements AutoCompleteDataSource {

	protected AbstractAutoCompleteDataSource(GlobalAutoCompleteDataSource source) {
		source.add(this);
	}
	
}
