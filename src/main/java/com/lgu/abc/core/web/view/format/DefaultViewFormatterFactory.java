package com.lgu.abc.core.web.view.format;

import com.lgu.abc.core.prototype.org.user.User;

public class DefaultViewFormatterFactory implements FormatterFactory {

	private final Formattable formatter = new DefaultViewFormatter();
	
	@Override
	public Formattable formattable(User user) {
		return formatter;
	}

}
