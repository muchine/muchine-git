package com.lgu.abc.core.plugin.ui.widget.factory;

import java.util.List;

import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.prototype.org.user.User;

public class UserWidgetFetcher implements WidgetFetchable {

	private final User user;
	
	public UserWidgetFetcher(User user) {
		this.user = user;
	}
	
	@Override
	public List<Widget> fetch(WidgetFactory factory) {
		return factory.create(user);
	}

}
