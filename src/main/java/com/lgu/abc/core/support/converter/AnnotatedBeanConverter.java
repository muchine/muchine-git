package com.lgu.abc.core.support.converter;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.utils.EntityUtils;
import com.lgu.abc.core.support.annotation.converter.Name;
import com.lgu.abc.core.support.field.processor.FieldProcessor;
import com.lgu.abc.core.support.field.processor.FieldProcessorTemplate;

@Component
public class AnnotatedBeanConverter {

	public <T, B> void beanize(final T entity, final B bean) {
		
		if (EntityUtils.isRootEntity(entity) && EntityUtils.isRootEntity(bean)) {
			RootEntity action = (RootEntity) entity;
			RootEntity beanized = (RootEntity) bean;
			
			beanized.setId(action.getId());
			
			beanized.writeBaseProperty(action);
			beanized.writeRootProperty(action);
		}
		
		FieldProcessorTemplate template = new FieldProcessorTemplate();
		template.process(bean, new FieldProcessor() {

			@Override
			public void process(Field field, Object object) throws Exception {
				if (field.getAnnotation(Name.class) != null) {
					try {
						Field target = entity.getClass().getDeclaredField(field.getName());
						target.setAccessible(true);
						
						field.set(bean, target.get(entity));
					}
					catch (NoSuchFieldException e) {
						throw new IllegalStateException(e);
					}
					catch (IllegalAccessException e) {
						throw new IllegalStateException(e);
					}
				}
			}
			
		});
	}
	
	public <T, B> void materialize(final T entity, final B bean) {
		if (EntityUtils.isRootEntity(entity) && EntityUtils.isRootEntity(bean)) {
			RootEntity action = (RootEntity) entity;
			RootEntity beanized = (RootEntity) bean;
			
			action.setId(beanized.getId());
			
			action.writeBaseProperty(beanized);
			action.writeRootProperty(beanized);
		}
		
		FieldProcessorTemplate template = new FieldProcessorTemplate();
		template.process(bean, new FieldProcessor() {

			@Override
			public void process(Field field, Object object) throws Exception {
				if (field.getAnnotation(Name.class) != null) {
					try {
						Field target = entity.getClass().getDeclaredField(field.getName());
						target.setAccessible(true);
						
						target.set(entity, field.get(bean));
					}
					catch (NoSuchFieldException e) {
						throw new IllegalStateException(e);
					}
					catch (IllegalAccessException e) {
						throw new IllegalStateException(e);
					}
				}
			}
			
		});
	}
}
