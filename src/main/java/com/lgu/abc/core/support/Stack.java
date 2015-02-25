package com.lgu.abc.core.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Stack {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	private List<Object> stack = new ArrayList<Object>();
	
	public void add(Object entity) {
		this.stack.add(entity);
		logger.trace("added to stack [" + this.stack.size() + "]: " + entity.getClass());
	}
	
	public void remove(Object entity) {
		this.stack.remove(entity);
		logger.trace("remove from stack [" + this.stack.size() + "]: " + entity.getClass());
	}
	
	public boolean contains(Object entity) {
		return stack.contains(entity);
	}
}
