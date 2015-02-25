package com.lgu.abc.core.plugin.ui.feed.embed;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class NewsFeedEmbedderRegistry {

	private static final Set<NewsFeedEmbeddable> embeddables = new HashSet<NewsFeedEmbeddable>();
	
	public synchronized void add(NewsFeedEmbeddable embedder) {
		PluggableUtils.add(embedder, embeddables);
	}
	
	public static NewsFeedEmbeddable find(String embedderId, Company company) {
		return PluggableUtils.find(embedderId, company, embeddables);
	}
	
	public static List<NewsFeedEmbeddable> enabled(Company company) {
		return PluggableUtils.enabled(company, embeddables);
	}
	
	public static Set<NewsFeedEmbeddable> all() {
		return PluggableUtils.all(embeddables);
	}
	
}
