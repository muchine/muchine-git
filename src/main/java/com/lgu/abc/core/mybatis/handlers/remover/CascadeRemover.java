package com.lgu.abc.core.mybatis.handlers.remover;

public interface CascadeRemover<T> {
	
	void removeCascade(T entity);
	
}
