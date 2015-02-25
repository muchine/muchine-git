package com.lgu.abc.core.support;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.action.ActionResult;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.interfaces.Cleanable;
import com.lgu.abc.core.support.field.processor.FieldProcessor;
import com.lgu.abc.core.support.field.processor.FieldProcessorTemplate;

public class Cleaner {
	
	protected static final Log logger = LogFactory.getLog(Cleaner.class);
	
	private static List<Class<?>> excluded = new ArrayList<Class<?>>();
	
	static {
		initialize();
	}
	
	private static void initialize() {
		excluded.add(Locale.class);
		excluded.add(ActionResult.class);
	}
	
	public static void clean(final RootEntity entity) {
		logger.debug("cleaning entity...");
		
		FieldProcessorTemplate template = new FieldProcessorTemplate();
		template.process(entity, new FieldProcessor() {
			
			@Override
			public void process(Field field, Object object) throws Exception {
				if (Cleanable.class.isAssignableFrom(field.getType())) {
					Cleanable cleanable = (Cleanable) field.get(entity);
					if (cleanable != null) cleanable.clean();
				}
				else {
					if (canSkip(field)) return;
					field.set(entity, null);
				}
			}
			
		});
	}
	
	private static boolean canSkip(Field field) {
		return excluded.contains(field.getType()) || field.getType().isPrimitive() || isFinal(field);
	}
	
	private static boolean isFinal(Field field) {
		return Modifier.isFinal(field.getModifiers());
	}
	
}
