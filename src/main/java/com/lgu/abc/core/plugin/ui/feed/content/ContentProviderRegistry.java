package com.lgu.abc.core.plugin.ui.feed.content;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class ContentProviderRegistry {

	private static final Set<ContentProvidable> providers = new HashSet<ContentProvidable>();
	
	public static void add(ContentProvidable provider) {
		PluggableUtils.add(provider, providers);
	}
	
	public static ContentProvidable find(String providerId, Company company) {
		return PluggableUtils.find(providerId, company, providers);
	}
		
	public static List<ContentProvidable> enabled(Company company) {
		return PluggableUtils.enabled(company, providers);
	}
	
	public static Set<ContentProvidable> all() {
		return PluggableUtils.all(providers);
	}
	
}
