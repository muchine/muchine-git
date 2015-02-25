package com.lgu.abc.core.plugin.ui.feed.embed;

import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.base.plugin.ModulePluggable;
import com.lgu.abc.core.plugin.ui.feed.content.ContentProperty;
import com.lgu.abc.core.prototype.org.user.User;

public interface NewsFeedEmbeddable extends ModulePluggable {

	String icon();
	
	ModelAndView load(ContentProperty content, User actor);
	
}
