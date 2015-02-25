package com.lgu.abc.core.mybatis.handlers.graph.annotation;

public interface DirtyCheckable {

	boolean isDirty();
	
	boolean isDeleted();
	
}
