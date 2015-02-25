package com.lgu.abc.core.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.Listable;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.base.service.DomainService;
import com.lgu.abc.core.exception.http.MethodNotAllowedException;
import com.lgu.abc.core.web.security.authorizer.Authorizer;
import com.lgu.abc.core.web.security.filter.Filter;
import com.u2ware.springfield.controller.HandlerMetamodel;
import com.u2ware.springfield.domain.ValidationRejectableException;

/**
 * Handle multiple creation, update and deletion requests. Default CRUD operations are handled by BaseController.
 * 
 * @author sejoon
 *
 * @param <L>
 * @param <T>
 * @param <Q>
 */
public abstract class DomainController<L extends Listable<T>, T extends RootEntity, Q extends BaseEntity>
		extends BaseController<T, Q> {

	private DomainService<T, Q> service;
	
	public DomainController(DomainService<T, Q> service, HandlerMetamodel<T, Q> metamodel, Authorizer<T, Q> authorizer) {
		super(service, metamodel, authorizer);
		this.service = service;
	}
	
	public DomainController(DomainService<T, Q> service, HandlerMetamodel<T, Q> metamodel, Authorizer<T, Q> authorizer, Filter<T, Q> filter) {
		super(service, metamodel, authorizer, filter);
		this.service = service;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/new/list")
	public String create(Model model, @ModelAttribute("model_entities") @Validated L entities,
			BindingResult errors) throws Exception {

		return this.create(model, entities.getEntities(), errors);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/list")
	public String update(Model model, @ModelAttribute("model_entities") @Validated L entities, BindingResult errors) throws Exception {

		return this.update(model, entities.getEntities(), errors);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/list")
	public String delete(Model model, @ModelAttribute("model_entities") L entities, BindingResult errors) throws Exception {
		
		return this.delete(model, entities.getEntities(), errors);
	}
	
	/*
	 * Actual action methods
	 */
	
	/**
	 * Handle multiple creation request.
	 * 
	 * @param model
	 * @param entities
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	private String create(Model model, List<T> entities, BindingResult errors) throws Exception {
		
		validate(OperationType.MULTI_CREATE, entities);
		
		try {
			// Prepare all entities in the list for creation
			for (T entity : entities) {
				supporter.prepareCreation(entity, errors);
				
				if (errors.hasErrors())
					return this.resolveViewName(model, "create", entities, null, null, null);
			}			
							
			List<T> result = this.service.create(entities);			
			return this.resolveViewName(model, "create", result, null, null, null);
		}
		catch (ValidationRejectableException e) {
			super.resolveValidation(errors, e);
			return this.resolveViewName(model, "create", entities, null, null, null);
		}
	}
	
	/**
	 * Handle multiple update request.
	 * 
	 * @param model
	 * @param entities
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	private String update(Model model, List<T> entities, BindingResult errors) throws Exception {
		
		validate(OperationType.MULTI_UPDATE, entities);
		
		// TODO validate if entity has id.		
		
		try {
			// Prepare all entities in the list for update
			for (T entity : entities) {
				supporter.prepareUpdate(entity, errors);
				
				if (errors.hasErrors())
					return this.resolveViewName(model, "update", entities, null, null, null);
			}
			
			List<T> result = this.service.update(entities);
			return this.resolveViewName(model, "update", result, null, null, null);
		}
		catch (ValidationRejectableException e) {
			super.resolveValidation(errors, e);
			return this.resolveViewName(model, "update", entities, null, null, null);
		}
	}

	/**
	 * Handle multiple deletion request.
	 * 
	 * @param model
	 * @param entities
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	private String delete(Model model, List<T> entities, BindingResult errors) throws Exception {

		validate(OperationType.MULTI_DELETE, entities);
		
		// TODO validate if entity has id.

		try {
			// Prepare all entity in the list for deletion 
			for (T entity : entities) {
				supporter.prepareDeletion(entity, errors);
				
				if (errors.hasErrors())
					return this.resolveViewName(model, "delete", entities, null, null, null);
			}
			
			List<T> result = this.service.delete(entities);
			return this.resolveViewName(model, "delete", result, null, null, null);
		} 
		catch (ValidationRejectableException e) {
			super.resolveValidation(errors, e);
			return this.resolveViewName(model, "delete", entities, null, null, null);
		}
	}
	
	@Override
	protected int getQueryParameterIndex() {
		return 2;
	}

	/*
	 * Helper methods
	 */
	private void validate(OperationType type, List<T> entities) {
		if (!canSupport(type)) throw new MethodNotAllowedException();
		
		Validate.notNull(entities, "entities are null.");
		Validate.notNull(service, "domain service is null.");
	}
	
	private String resolveViewName(Model model, String commandMethod,
			List<T> entities, Q query, Pageable pageable, Object queryResult)
			throws Exception {

		HandlerMetamodel<T, Q> metamodel = this.getMetamodel();

		model.addAttribute(MODEL_ENTITY, entities);
		model.addAttribute(MODEL_ENTITY_METAMODEL, metamodel);

		model.addAttribute(MODEL_QUERY, query);
		model.addAttribute(MODEL_QUERY_PAGEABLE, pageable);
		model.addAttribute(MODEL_QUERY_RESULT, queryResult);

		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = attrs.getRequest();

		String commandMethodAppend = "";

		StringBuilder sb = new StringBuilder();
		
		if (entities != null) {
			for (T entity : entities) {
				if (sb.length() > 1) sb.append(",");
				sb.append(metamodel.getIdentityUri(entity));
			}
		}
		
		String commandId = sb.toString();
		String requestUri = request.getRequestURI();
		String extension = StringUtils.getFilenameExtension(requestUri);
		String viewName = metamodel.getTopLevelMapping() + "/" + commandMethod
				+ commandMethodAppend
				+ (StringUtils.hasText(extension) ? "." + extension : "");

		if (StringUtils.hasText(metamodel.getAttributesCSV())) {
			viewName = viewName + "?" + metamodel.getAttributesCSV();
		}

		// logger.info("command_seq: "+metamodel.getSeq());
		logger.info("command_path: " + metamodel.getTopLevelMapping());
		logger.info("command_id: " + commandId);
		logger.info("command_method: " + commandMethod);
		logger.info("command_extension: " + extension);
		logger.info("command_view: " + viewName);

		model.addAttribute(COMMAND_PATH, metamodel.getTopLevelMapping());
		model.addAttribute(COMMAND_ID, commandId);
		model.addAttribute(COMMAND_METHOD, commandMethod);
		model.addAttribute(COMMAND_EXTENSION, extension == null ? "" : "."
				+ extension);
		model.addAttribute(COMMAND_VIEW, viewName);

		return viewName;
	}
	
}
