package com.lgu.abc.core.base.controller;

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.ActionResult;
import com.lgu.abc.core.base.domain.action.ActionResult.ResultType;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.query.BaseQuery;
import com.lgu.abc.core.base.operation.Action;
import com.lgu.abc.core.common.infra.log.Logger;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.Cleaner;
import com.lgu.abc.core.support.bundle.MessageBundleManager;
import com.lgu.abc.core.web.controller.AuthorizationHandler;
import com.lgu.abc.core.web.controller.ControllerUtil;
import com.lgu.abc.core.web.controller.FilterHandler;
import com.lgu.abc.core.web.security.authorizer.Authorizer;
import com.lgu.abc.core.web.security.filter.Filter;
import com.u2ware.springfield.controller.EntityHandler;
import com.u2ware.springfield.domain.EntityPageable;

public class ControllerSupporter<T extends RootEntity, Q extends BaseEntity> {

	private final Logger logger = new Logger(getClass());
	
	private final BaseController<T, Q> controller;
	
	private final AuthorizationHandler<T, Q> authorizer;
	
	private final FilterHandler<T, Q> filter;
	
	private final Class<T> type;
		
	public ControllerSupporter(BaseController<T, Q> controller, Authorizer<T, Q> authorizer, Filter<T, Q> filter) {
		Validate.notNull(controller, "controller is null.");
		
		this.controller = controller;
		this.type = controller.getMetamodel().getEntityClass();
		
		this.authorizer = new AuthorizationHandler<T, Q>(authorizer, type);
		this.filter = new FilterHandler<T, Q>(filter);
	}
	
	public T prepareCreation(T entity, BindingResult errors) {
		if (handleBindingError(entity, errors)) return entity;
		
		setAction(entity, Action.CREATE);
		injectSession(entity);
		if (!filter.filterEntity(entity, errors)) return entity;
				
		entity.prepareCreation();
		
		if (!authorizer.authorizeCreation(entity, errors)) return cleanup(entity);
		
		generateId(entity);
		prepareAction(entity);
		setActionResult(errors, entity);
		
		logger.info(entity, "create entity: " + entity);
		return entity;
	}
	
	public T prepareUpdate(T entity, BindingResult errors) {
		if (handleBindingError(entity, errors)) return entity;
		
		setAction(entity, Action.UPDATE);
		injectSession(entity);
		if (!filter.filterEntity(entity, errors)) return entity;

		entity.prepareUpdate();

		if (!authorizer.authorizeUpdate(entity, errors)) return cleanup(entity);
		
		prepareAction(entity);
		setActionResult(errors, entity);
	
		logger.info(entity, "update entity: " + entity);
		return entity;
	}

	public T prepareDeletion(T entity, BindingResult errors) {
		if (handleBindingError(entity, errors)) return entity;
		
		setAction(entity, Action.DELETE);
		injectSession(entity);
		if (!filter.filterEntity(entity, errors)) return entity;
		entity.initialize();
		
		if (!authorizer.authorizeDeletion(entity, errors)) return cleanup(entity);
		
		setActionResult(errors, entity);
		
		logger.debug("entity delete: " + entity.getId());
		logger.info(entity, "delete entity: " + entity);
		
		return entity;
	}
	
	public void prepareRead(BaseEntity entity) {
		injectSession(entity);
		entity.initialize();
		
		logger.debug("prepare read entity: " + entity);
	}
	
	public void postRead(Model model, T entity, BindingResult errors) {
		if (!authorizer.authorizeEntityResult(entity, model, errors))
			model.addAttribute(EntityHandler.MODEL_ENTITY, cleanup(entity));
		
		setActionResult(errors, ControllerUtil.getEntity(model, type));
	}

	public void prepareQuery(Q query, BindingResult errors, EntityPageable pageable) {
		if (errors.hasErrors()) return;
		
		if (query instanceof BaseQuery) ((BaseQuery) query).setPageable(pageable);
		
		setAction(query, Action.FIND);
		if (!filter.filterQuery(query, errors)) return;
		injectSession(query);
		query.initialize();
		
		logger.debug("query: " + query);
	}
	
	public void postQuery(Q query, Model model, BindingResult errors) {
		authorizer.authorizeQueryResult(query, model, errors);
	}
	
	/*
	 * Helper methods
	 */
	private void injectSession(BaseEntity entity) {
		User session = controller.getSessionManager().getSession();
		Validate.notNull(session, "user session is null.");
		
		entity.setActor(session);
		logger.trace("Inject Session: " + entity.getActor().getId());
	}
	
	private void generateId(BaseEntity entity) {
		if (entity.isNull())
			entity.setId(controller.getSequenceManager().generateId(entity));
	}
	
	private void setAction(BaseEntity entity, Action action) {
		if (entity.getAction() == null) entity.setAction(action);
	}
	
	private void setActionResult(BindingResult errors, T entity) {
		if (entity == null || entity.getResult() != null) return;
		
		String message = getMessageFromBindingResult(errors, entity.getLocale());
		ActionResult result = new ActionResult(!errors.hasErrors(), message);
		
		entity.setResult(result);
	}
	
	private String getMessageFromBindingResult(BindingResult errors, Locale locale) {
		if (!errors.hasErrors()) return "";
		
		String code = errors.getAllErrors().get(0).getCode();
		return MessageBundleManager.get(code, locale);
	}
	
	private boolean handleBindingError(T entity, BindingResult errors) {
		if (!errors.hasErrors()) return false;
		
		setActionResult(errors, entity);
		return true;
	}
	
	/*
	 * Preparation methods
	 */
	private void prepareAction(RootEntity entity) {
		entity.initialize();
	}
	
	private T cleanup(T entity) {
		try {
			Cleaner.clean(entity);
			entity.setResult(new ActionResult(ResultType.FAILURE, getAuthorizationErrorMessage(Locale.KOREAN)));
			
			return entity;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getAuthorizationErrorMessage(Locale locale) {
		return MessageBundleManager.get("action.error.unauthorized", locale);
	}
	
}
