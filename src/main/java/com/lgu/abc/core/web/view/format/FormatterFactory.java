package com.lgu.abc.core.web.view.format;

import com.lgu.abc.core.prototype.org.user.User;

public interface FormatterFactory {

	Formattable formattable(User user);
	
}
