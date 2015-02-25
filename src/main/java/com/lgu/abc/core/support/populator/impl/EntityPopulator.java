package com.lgu.abc.core.support.populator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.base.utils.EntityUtils;
import com.lgu.abc.core.support.Stack;
import com.lgu.abc.core.support.annotation.populator.Replace;
import com.lgu.abc.core.support.field.processor.FieldProcessor;
import com.lgu.abc.core.support.field.processor.FieldProcessorTemplate;
import com.lgu.abc.core.support.populator.AbstractPopulator;
import com.lgu.abc.core.support.populator.Populator;
import com.lgu.abc.core.support.populator.PopulatorFactory;
import com.lgu.abc.core.support.populator.PopulationSupporter;

public class EntityPopulator extends AbstractPopulator implements Populator {
	
	private final PopulatorFactory factory;
	
	private final Stack stack;
		
	private final boolean forced;
	
	private static final String[] bypassing = new String[] {"actor", "action"};
		
	public EntityPopulator(PopulatorFactory factory, boolean forced) {
		this.factory = factory;
		
		this.stack = new Stack();
		this.forced = forced;
	}
	
	@Override
	public Object populate(final Object source, final Object target) {
		if (!canPopulate(source, target)) return target; 
		
		stack.add(target);
		
		try {
			new FieldProcessorTemplate().process(target, new FieldProcessor() {

				@Override
				public void process(Field field, Object object) throws Exception {
					if (canBypass(field)) {
						logger.trace("bypass populating the field: " + field.getName());
						return;
					}
					
					Method method = EntityUtils.findGetter(field.getName(), target);
					if (method == null) {
						logger.trace("getter method is null. field name: " + field.getName());
						return;
					}
					
					Object sourceField = method.invoke(source);
					Object targetField = method.invoke(target);
					
					logger.trace("target field: " + targetField);
					
					if (canReplace(field, sourceField, targetField)) {
						logger.trace("populate the field: " + field.getName() + ", value: " + sourceField);
						set(field, sourceField, target);
						return;
					}
					
					Populator populator = factory.create(field);
					if (!populator.canPopulate(sourceField, targetField)) {
						logger.trace("skip populating the field " + field.getName() + "... value: " + targetField);
						return;
					}
					
					set(field, populator.populate(sourceField, targetField), target);
				}
			});
		}
		finally {
			stack.remove(target);
		}
		
		return target;
	}
	
	private void set(Field field, Object source, Object target) throws Exception {
		field.set(target, source);
	}
	
	@Override
	public boolean canPopulate(Object source, Object target) {
		if (!super.canPopulate(source, target)) return false;
		
		if (stack.contains(target)) {
			logger.debug("Cross referenced object. Skip the population since this object is already being populated. Class: " + target.getClass());
			return false;
		}
		
		if (!(target instanceof ActionEntity) && !EntityUtils.isDomainObject(target)) {
			logger.trace("target is not supported type: " + target.getClass());
			return false;
		}
		
		return true;
	}
	
	private boolean canBypass(Field field) {
		for (String bypass : bypassing)
			if (bypass.equals(field.getName())) return true;
		
		return false;
	}
	
	private boolean canReplace(Field field, Object source, Object target) {
		if (forced) return true;
		if (PopulationSupporter.shouldPreserve(field)) return false;
		
		return isEmpty(target) || (source != null && field.getAnnotation(Replace.class) != null);
	}
		
	private boolean isEmpty(Object object) {
		if (object == null) return true;
		if (object instanceof List) return ((List<?>) object).isEmpty();
		
		return false;
	}

}
