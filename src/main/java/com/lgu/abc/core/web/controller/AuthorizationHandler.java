package com.lgu.abc.core.web.controller;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.web.security.authorizer.Authorizer;
import com.u2ware.springfield.controller.EntityHandler;
import com.u2ware.springfield.domain.EntityPageImpl;

public class AuthorizationHandler<T extends RootEntity, Q extends BaseEntity> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final Authorizer<T, Q> authorizer;
	
	private final Class<T> type;
	
	public AuthorizationHandler(Authorizer<T, Q> authorizer, Class<T> type) {
		Validate.notNull(authorizer, "authorizer is null.");
		
		this.authorizer = authorizer;
		this.type = type;
	}
	
	public boolean authorizeCreation(T entity, BindingResult errors) {
		if (!authorizer.authorizeCreation(entity)) {
			addEntityAuthorizationError(entity, errors);
			return false;
		}
		
		return true;
	}
	
	public boolean authorizeUpdate(T entity, BindingResult errors) {
		if (!authorizer.authorizeUpdate(entity)) {
			addEntityAuthorizationError(entity, errors);
			return false;
		}
		
		return true;
	}
	
	public boolean authorizeDeletion(T entity, BindingResult errors) {
		if (!authorizer.authorizeDeletion(entity)) {
			addEntityAuthorizationError(entity, errors);
			return false;
		}
		
		return true;
	}
		
	@SuppressWarnings("unchecked")
	public boolean authorizeEntityResult(T entity, Model model, BindingResult errors) {
		Object result = model.asMap().get(EntityHandler.MODEL_ENTITY);
		if (result == null) return true;
		
		Assert.isTrue(type.isAssignableFrom(result.getClass()));
		
		if (!authorizer.authorizeEntity(entity, (T) result)) {
			addEntityAuthorizationError(entity, errors);
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void authorizeQueryResult(Q query, Model model, BindingResult errors) {
		Iterable<T> result = (Iterable<T>) model.asMap().get(EntityHandler.MODEL_QUERY_RESULT);
		if (result == null) return;
		
		if (authorizer.authorizeQuery(query, result)) return;
		
		model.addAttribute(EntityHandler.MODEL_QUERY_RESULT, new EntityPageImpl<T>());
		addQueryAuthorizationError(query, errors);
	}
		
	private void addEntityAuthorizationError(T entity, BindingResult errors) {
		logger.warn("Authorization failure. entity: " + entity);
		errors.reject("authorization", "Not authorized request.");
	}
	
	private void addQueryAuthorizationError(Q query, BindingResult errors) {
		logger.warn("Authorization failure. query: " + query);
		errors.reject("authorization", "Not authorized request. query: " + query);
	}
	
}
