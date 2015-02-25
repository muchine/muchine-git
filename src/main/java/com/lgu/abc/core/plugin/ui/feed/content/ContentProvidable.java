package com.lgu.abc.core.plugin.ui.feed.content;

import com.lgu.abc.core.base.plugin.ModulePluggable;
import com.lgu.abc.core.plugin.ui.feed.exception.ContentException;
import com.lgu.abc.core.prototype.org.user.User;

public interface ContentProvidable extends ModulePluggable {

	ContentProperty provide(String id, User actor) throws ContentException;
	
}
