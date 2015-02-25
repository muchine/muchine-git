package com.lgu.abc.core.web.view.format;

import com.lgu.abc.core.prototype.org.user.User;

public class FormatterRegistry {
	
	private static FormatterFactory factory = new DefaultViewFormatterFactory();
	
	public static synchronized void set(FormatterFactory instance) {
		factory = instance;
	}
	
	public static Formattable formattable(User user) {
		return factory.formattable(user);
	}
	
}
