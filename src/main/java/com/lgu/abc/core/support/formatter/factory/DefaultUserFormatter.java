package com.lgu.abc.core.support.formatter.factory;

import java.util.Locale;

import com.lgu.abc.core.prototype.org.user.User;

public class DefaultUserFormatter implements UserFormattable {

	@Override
	public String format(User user, Locale locale) {
		return user.getName().toString();
	}
	
	@Override
	public String designate(User user, Locale locale) {
		return user.getName().toString();
	}

	@Override
	public int order() {
		return 100;
	}

}
