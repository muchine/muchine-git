package com.lgu.abc.core.base.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.action.ShareableEntity;
import com.lgu.abc.core.base.domain.query.BaseQuery;
import com.lgu.abc.core.support.annotation.Domain;
import com.lgu.abc.core.support.id.annotation.Sequence;

public class EntityUtils {
	
	protected static final Log logger = LogFactory.getLog(EntityUtils.class);
	
	private static final String[] getters = new String[] {"get", "is"};
	
	public static boolean isBaseEntity(Object object) {
		Validate.notNull(object, "entity is null.");
		return BaseEntity.class.isAssignableFrom(object.getClass());
	}
	
	public static boolean isRootEntity(Object object) {
		Validate.notNull(object, "entity is null.");
		return RootEntity.class.isAssignableFrom(object.getClass());
	}
	
	public static boolean isShareableEntity(Object object) {
		Validate.notNull(object, "entity is null.");
		return ShareableEntity.class.isAssignableFrom(object.getClass());
	}
	
	public static boolean isBaseQuery(Object object) {
		Validate.notNull(object, "query is null.");
		return BaseQuery.class.isAssignableFrom(object.getClass());
	}
	
	public static boolean isCollection(Object object) {
		Validate.notNull(object, "object");
		return Collection.class.isAssignableFrom(object.getClass());
	}
	
	public static boolean isList(Object object) {
		Validate.notNull(object, "object");
		return List.class.isAssignableFrom(object.getClass());
	}
	
	public static boolean isActionEntityList(Object object) {
		if (!isList(object)) return false;
		
		List<?> casted = (List<?>) object;
		if (casted.isEmpty()) return false;
		
		return isRootEntity(casted.get(0));
	}
	
	public static boolean isDomainObject(Object object) {
		// Look up if Domain annotation exists
		Validate.notNull(object, "object is null.");
		return object.getClass().getAnnotation(Domain.class) != null;
	}

	public static void validateIfBaseEntity(Object object) {
		Assert.isTrue(isBaseEntity(object), "object is not inhereted from base entity.");
	}
	
	public static String getSequenceTable(Class<?> type) {
		Sequence annotation = type.getAnnotation(Sequence.class);
		return annotation == null ? null : annotation.table();
	}
	
	public static String getSequenceField(Class<?> type) {
		Sequence annotation = type.getAnnotation(Sequence.class);
		return annotation == null ? null : annotation.field();
	}
	
	public static Class<?> getActualClass(Class<?> type) {
		return Enhancer.isEnhanced(type) ? type.getSuperclass() : type;
	}
	
	public static Method findGetter(String fieldName, Object source) {
		return method(source, methodNames(fieldName, getters));
	}
	
	private static List<String> methodNames(String fieldName, String[] prefixes) {
		Validate.notNull(fieldName, "fieldName is null");
		
		List<String> names = new ArrayList<String>();
		
		for (String prefix : prefixes)
			names.add(prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
		
		names.add(fieldName);
		return names;
	}
	
	private static Method method(Object source, Iterable<String> names) {
		Validate.notNull(source, "source is null");
		
		for (String name : names) {
			try {
				if (isMethodExist(name, source.getClass())) return source.getClass().getMethod(name);
			}
			catch (NoSuchMethodException e) {
				throw new IllegalStateException(e);
			}
		}
		
		logger.trace("No method has been found by names: " + names);
		return null;
	}
	
	private static boolean isMethodExist(String name, Class<?> type) {
		Method[] methods = type.getMethods();
		
		for (Method m : methods) {
			if (m.getName().equals(name)) return true;
		}
		
		return false;
	}
	
}
