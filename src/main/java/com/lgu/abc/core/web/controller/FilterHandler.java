package com.lgu.abc.core.web.controller;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.support.bundle.MessageBundleManager;
import com.lgu.abc.core.web.security.filter.Filter;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;

public class FilterHandler<T extends RootEntity, Q extends BaseEntity> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final Filter<T, Q> filter;
	
	public FilterHandler(Filter<T, Q> filter) {
		Validate.notNull(filter, "filter is null.");
		this.filter = filter;
	}
	
	public boolean filterEntity(T entity, BindingResult errors) {
		try {
			filter.filterEntity(entity);
			return true;
		}
		catch (InvalidParameterException e) {
			logger.debug("message: " + e.getMessage());
			final String message = StringUtils.isEmpty(e.getMessage()) ? getDefaultMessage(entity.getLocale()) : e.getMessage();
			
			entity.fail(message);
			errors.addError(new ObjectError(e.getObjectName(), new String[] {"parameter"}, null, e.getMessage()));
			return false;
		}
	}
	
	public boolean filterQuery(Q query, BindingResult errors) {
		try {
			filter.filterQuery(query);
			return true;
		}
		catch (InvalidParameterException e) {
			errors.addError(new ObjectError(e.getObjectName(), new String[] {"parameter"}, null, e.getMessage()));
			return false;
		}
	}
	
	private String getDefaultMessage(Locale locale) {
		return MessageBundleManager.get("action.error.filter", locale);
	}
	
}
