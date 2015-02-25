package com.lgu.abc.core.support.field.processor;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FieldProcessorTemplate {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final boolean navigateSuperClass;
	
	public FieldProcessorTemplate() {
		this(true);
	}
	
	public FieldProcessorTemplate(boolean navigateSuperClass) {
		this.navigateSuperClass = navigateSuperClass;
	}

	public void process(final Object object, final FieldProcessor processor) {
		logger.trace("object class: " + object.getClass());
		logger.trace("processor: " + processor);
		
		Class<?> root = navigateSuperClass ? Object.class : object.getClass().getSuperclass();
		
		try {
			for (Class<?> type = object.getClass(); type != root; type = type.getSuperclass()) {
				Field[] fields = type.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);					
					processor.process(field, object);
				}
			}
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}				
	}
	
}
