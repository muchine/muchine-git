package com.lgu.abc.core.support.converter;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.exception.BeanConversionException;

public class ExceptionProcessingConverter<T, B> implements Converter<T, B> {
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	private final Converter<T, B> converter;
	
	public ExceptionProcessingConverter(Converter<T, B> converter) {
		Validate.notNull(converter);
		this.converter = converter;
	}

	@Override
	public B beanize(T entity) {
		try {
			logger.trace("beanize entity: " + entity);
			B bean = this.converter.beanize(entity);
			logger.trace("bean: " + bean);
			
			return bean;
		}
		catch (RuntimeException e) {
			throw new BeanConversionException("Failure to beanize entity: " + entity, e);
		}
	}

	@Override
	public T materialize(B bean) {
		try {
			logger.trace("materialize entity: " + bean);
			T entity = this.converter.materialize(bean);
			logger.trace("entity: " + entity);
			
			return entity;
		}
		catch (RuntimeException e) {
			throw new BeanConversionException("Failure to materialize bean: " + bean, e);
		}
	}

}
