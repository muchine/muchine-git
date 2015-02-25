package com.lgu.abc.core.web.controller;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.prototype.org.user.User;
import com.u2ware.springfield.controller.EntityHandler;

public class ControllerUtil {
	
	private ControllerUtil() {}

	public static String getBaseUrl(Object controller) {
		RequestMapping mapping = controller.getClass().getAnnotation(RequestMapping.class);
		if (mapping == null) throw new IllegalStateException("Request mapping is null.");
		
		return mapping.value()[0];
	}
	
	public static Class<?> getQueryClass(Class<?> controllerClass, int typeIndex) {
		return ((Class<?>)((ParameterizedType) controllerClass.getGenericSuperclass()).getActualTypeArguments()[typeIndex]);
	}
	
	public static Class<?> getCommandClass(Class<?> controllerClass, int typeIndex) {
		return ((Class<?>)((ParameterizedType) controllerClass.getGenericSuperclass()).getActualTypeArguments()[typeIndex]);
	}
		
	public static <T> T getEntity(Model model, Class<T> type) {
		return getEntity(model.asMap(), type);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getEntity(Map<String, Object> model, Class<T> type) {
		return (T) model.get(EntityHandler.MODEL_ENTITY);
	}
	
	public static <T> Iterable<T> getQueryResult(Model model, Class<T> type) {
		return getQueryResult(model.asMap(), type);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Iterable<T> getQueryResult(Map<String, Object> model, Class<T> type) {
		return (Iterable<T>) model.get(EntityHandler.MODEL_QUERY_RESULT);
	}
	
	public static <T extends RootEntity> Iterable<T> injectActor(Iterable<T> entities, User actor) {
		List<T> injected = new ArrayList<T>();
		if (IterableUtils.isEmpty(entities)) return injected;
		
		for (T entity : entities) {
			entity.setActor(actor);
			injected.add(entity);
		}
		
		return injected;
	}
		
}
