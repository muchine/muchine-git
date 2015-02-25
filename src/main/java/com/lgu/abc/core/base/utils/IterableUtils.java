package com.lgu.abc.core.base.utils;

import java.util.ArrayList;
import java.util.List;

public class IterableUtils {

	public static <T> boolean isEmpty(Iterable<T> iterable) {
		return iterable == null || iterable.iterator() == null || !iterable.iterator().hasNext();
	}
	
	public static <T> List<T> toList(Iterable<T> iterable) {
		List<T> list = new ArrayList<T>();
		if (isEmpty(iterable)) return list;	
		
		for (T element : iterable) list.add(element);
		
		return list;
	}
		
}
