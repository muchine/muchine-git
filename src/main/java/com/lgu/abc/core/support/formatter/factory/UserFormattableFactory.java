package com.lgu.abc.core.support.formatter.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.SortableComparator;

@Component
public class UserFormattableFactory {

	private static final List<UserFormattable> formattables = new ArrayList<UserFormattable>();
	
	public UserFormattableFactory() {
		add(new DefaultUserFormatter());
	}
	
	public synchronized void add(UserFormattable formattable) {
		formattables.add(formattable);
		Collections.sort(formattables, SortableComparator.instance());
	}
	
	public UserFormattable create() {
		return formattables.get(0);
	}
	
}
