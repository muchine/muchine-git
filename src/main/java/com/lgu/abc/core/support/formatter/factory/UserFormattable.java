package com.lgu.abc.core.support.formatter.factory;

import java.util.Locale;

import com.lgu.abc.core.base.plugin.Sortable;
import com.lgu.abc.core.prototype.org.user.User;

public interface UserFormattable extends Sortable {

	String format(User user, Locale locale);
	
	String designate(User user, Locale locale);
	
}
