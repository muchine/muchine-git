package com.lgu.abc.core.plugin.ui.feed.load;

import org.springframework.ui.Model;

import com.lgu.abc.core.base.plugin.ModulePluggable;
import com.lgu.abc.core.plugin.ui.feed.exception.ContentException;

public interface NewsFeedLoadable extends ModulePluggable {
	
	/**
	 * Return the type name of news feed loader which is used for search feeds in the feed repository.
	 * @return the type name of news feed loader
	 */
	String type();
	
	/**
	 * Return the regular expression to retrieve feeds
	 * @return the regular expression to retrieve feeds
	 */
	String query();
	
	String color();
	
	String load(Model model, String contentId) throws ContentException;
	
}
