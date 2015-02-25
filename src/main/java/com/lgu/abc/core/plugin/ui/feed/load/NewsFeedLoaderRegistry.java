package com.lgu.abc.core.plugin.ui.feed.load;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class NewsFeedLoaderRegistry {

	private static final Set<NewsFeedLoadable> loaders = new HashSet<NewsFeedLoadable>();
	
	public synchronized void add(NewsFeedLoadable loader) {
		PluggableUtils.add(loader, loaders);
	}
	
	public static NewsFeedLoadable find(String loaderId, Company company) {
		return PluggableUtils.find(loaderId, company, loaders);
	}
	
	public static NewsFeedLoadable findByType(String type) {
		for (NewsFeedLoadable loadable : all()) {
			if (type.startsWith(loadable.type())) return loadable;
		}
		
		return null;
	}
	
	public static List<NewsFeedLoadable> enabled(Company company) {
		return PluggableUtils.enabled(company, loaders);
	}
	
	public static Set<NewsFeedLoadable> all() {
		return PluggableUtils.all(loaders);
	}
	
}
