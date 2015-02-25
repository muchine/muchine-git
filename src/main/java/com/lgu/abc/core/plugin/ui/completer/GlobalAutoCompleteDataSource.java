package com.lgu.abc.core.plugin.ui.completer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.SortableComparator;
import com.lgu.abc.core.prototype.org.user.User;

@Component
public class GlobalAutoCompleteDataSource {

	private static final List<AutoCompleteDataSource> sources = new ArrayList<AutoCompleteDataSource>();
	
	public synchronized void add(AutoCompleteDataSource source) {
		if (find(source.id()) != null) 
			throw new IllegalArgumentException("completer already exists with id " + source.id());
		
		sources.add(source);
		Collections.sort(sources, SortableComparator.instance());
	}
	
	public List<AutoCompleteNode> nodes(User user, String... ids) {
		List<AutoCompleteNode> nodes = new ArrayList<AutoCompleteNode>();
		for (String id : ids) {
			AutoCompleteDataSource source = find(id);
			nodes.addAll(source.nodes(user));
		}
		
		Collections.sort(nodes);
		return nodes;
	}
	
	public AutoCompleteDataSource find(String id) {
		for (AutoCompleteDataSource source : sources) {
			if (source.id().equals(id)) return source;
		}
		
		return null;
	}
	
}
