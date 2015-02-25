package com.lgu.abc.core.support.id;

import java.util.List;
import java.util.Map;

/**
 * Sequence Monitor can track how sequence increases. Can be used mainly for test purpose.
 * However be careful because sequences are created in multi-thread environment.
 * 
 * @author sejoon
 */
public interface IdMonitor {
	
	public void add(Class<?> type, String id);
	
	public Map<Class<?>, List<String>> getAll();
	
	public List<String> getAll(Class<?> type);
	
	public String getFirst(Class<?> type);
	
	public String getLatest(Class<?> type);
	
	public void clear();
	
	public Integer count(Class<?> type);
	
}
