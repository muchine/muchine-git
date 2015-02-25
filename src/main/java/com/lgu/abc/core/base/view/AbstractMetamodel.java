package com.lgu.abc.core.base.view;

import com.u2ware.springfield.controller.HandlerMetamodel;

public abstract class AbstractMetamodel<T, Q> extends HandlerMetamodel<T, Q> {

	protected AbstractMetamodel(Class<T> entityClass, Class<Q> queryClass, String topLevelMapping) {
		super(entityClass, queryClass, topLevelMapping, new String[] {"*.jstl", "*.json"}, new String[] {"id"}, null);
	}
	
	protected AbstractMetamodel(Class<T> entityClass, Class<Q> queryClass, String topLevelMapping, String[] methodLevelMapping) {
		super(entityClass, queryClass, topLevelMapping, methodLevelMapping, new String[] {"id"}, null);
	}

}
