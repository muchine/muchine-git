package com.lgu.abc.core.base.controller.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgu.abc.core.base.controller.MacroExecutable;
import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.Listable;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.base.service.DomainService;
import com.lgu.abc.core.common.interfaces.Command;
import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.exception.http.MethodNotAllowedException;
import com.lgu.abc.core.web.controller.ControllerAuxiliary;
import com.lgu.abc.core.web.view.ViewResolver;
import com.u2ware.springfield.controller.HandlerMetamodel;
import com.u2ware.springfield.domain.ValidationRejectableException;

public abstract class MacroCommandController<T extends RootEntity, Q extends BaseEntity, L extends Listable<T>> extends 
		CommandController<T, Q> {

	private final DomainService<T, Q> service;
	
	private final ViewResolver<T, Q> resolver;
	
	public MacroCommandController(DomainService<T, Q> service, HandlerMetamodel<T, Q> metamodel, ControllerAuxiliary<T, Q> auxiliary) {
		super(service, metamodel, auxiliary);
		
		this.resolver = new ViewResolver<T, Q>(metamodel);
		this.service = service;
	}
		
	@RequestMapping(method = RequestMethod.POST, value = "/new/list")
	public String create(Model model, @ModelAttribute("model_entities") @Validated L commands, BindingResult errors) throws Exception {
		validate(OperationType.MULTI_CREATE, commands.getEntities());
		return doCreateWithCommand(model, commands.getEntities(), errors);
	}	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/list")
	public String update(Model model, @ModelAttribute("model_entities") @Validated L commands, BindingResult errors) throws Exception {
		validate(OperationType.MULTI_UPDATE, commands.getEntities());
		return doUpdateWithCommand(model, commands.getEntities(), errors);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/list")
	public String delete(Model model, @ModelAttribute("model_entities") L commands, BindingResult errors) throws Exception {
		validate(OperationType.MULTI_DELETE, commands.getEntities());
		return doDeleteWithCommand(model, commands.getEntities(), errors);
	}
	
	protected final String doCreateWithCommand(Model model, List<? extends Command> commands, BindingResult errors) throws Exception {
		return doCreateWithCommand(model, commands, errors, new MacroExecutable<T, Q>() {

			@Override
			public List<T> execute(DomainService<T, Q> service, List<T> entities) {
				return service.create(entities);
			}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	protected final String doCreateWithCommand(Model model, List<? extends Command> commands, BindingResult errors,
			MacroExecutable<T, Q> executable) throws Exception {
		List<T> entities = new ArrayList<T>();
		if (commands != null) {
			for (Command command : commands) entities.add((T) command.create());
		}
		
		return doCreate(model, entities, errors, executable);
	}
	
	protected final String doUpdateWithCommand(Model model, List<? extends Command> commands, BindingResult errors) throws Exception {
		return doUpdateWithCommand(model, commands, errors, new MacroExecutable<T, Q>() {

			@Override
			public List<T> execute(DomainService<T, Q> service, List<T> entities) {
				return service.update(entities);
			}
			
		});
	}
	
	protected final String doUpdateWithCommand(Model model, List<? extends Command> commands, BindingResult errors,
			MacroExecutable<T, Q> updatable) throws Exception {
		final List<T> entities = new ArrayList<T>();
		if (commands != null) {
			for (Command command : commands) entities.add(update(command, errors));	
		}
		
		return doUpdate(model, entities, errors, updatable);
	}
		
	protected final String doDeleteWithCommand(Model model, List<? extends Command> commands, BindingResult errors) throws Exception {
		List<T> entities = new ArrayList<T>();
		if (commands != null) {
			for (Command command : commands) entities.add(delete(command, errors));	
		}
		
		return doDelete(model, entities, errors);
	}
	
	private final String doCreate(Model model, List<T> entities, BindingResult errors, final MacroExecutable<T, Q> executable) throws Exception {
		return process(model, entities, errors, OperationType.MULTI_CREATE, "create", new Callable<T>() {

			@Override
			public List<T> process(List<T> entities, BindingResult errors) {
				for (T command : entities)
					supporter.prepareCreation(command, errors);
				
				if (errors.hasErrors()) return entities;
				try {
					return executable.execute(service, entities);
				} catch (CoreException e) {
					return createFailureResponses(e);
				}
			}
			
		});
	}
	
	private final String doUpdate(Model model, List<T> entities, BindingResult errors, final MacroExecutable<T, Q> updatable) throws Exception {
		return process(model, entities, errors, OperationType.MULTI_UPDATE, "update", new Callable<T>() {

			@Override
			public List<T> process(List<T> entities, BindingResult errors) {
				for (T command : entities)
					supporter.prepareUpdate(command, errors);
				
				if (errors.hasErrors()) return entities;
				try {
					return updatable.execute(service, entities);	
				} catch (CoreException e) {
					return createFailureResponses(e);
				}
			}
			
		});
	}
	
	private final String doDelete(Model model, List<T> entities, BindingResult errors) throws Exception {
		return process(model, entities, errors, OperationType.MULTI_DELETE, "delete", new Callable<T>() {

			@Override
			public List<T> process(List<T> entities, BindingResult errors) {
				for (T command : entities)
					supporter.prepareDeletion(command, errors);
				
				if (errors.hasErrors()) return entities;
				try {
					return service.delete(entities);
				} catch (CoreException e) {
					return createFailureResponses(e);
				}
			}
			
		});
	}
	
	private String process(Model model, List<T> entities, BindingResult errors,
			OperationType operationType, String method, Callable<T> callable) throws Exception {
		
		if (errors.hasErrors())	return resolver.resolveEntities(model, method, entities, null, null, null);
		
		try {
			List<T> result = callable.process(entities, errors);
			return resolver.resolveEntities(model, method, result, null, null, null);
		}
		catch (ValidationRejectableException e) {
			super.resolveValidation(errors, e);
			return resolver.resolveEntities(model, method, entities, null, null, null);
		}
	}
	
	/*
	 * Helper methods
	 */
	private void validate(OperationType type, List<T> commands) {
		if (!canSupport(type)) throw new MethodNotAllowedException();
		
		Validate.notNull(CollectionUtils.isEmpty(commands), "commands are null.");
		Validate.notNull(service, "domain service is null.");
	}
	
	private List<T> createFailureResponses(CoreException e) {
		List<T> responses = new ArrayList<T>();
		T response = createEntityObject();
		response.fail(e);
		
		responses.add(response);
		return responses;
	}
	
	private static interface Callable<T> {
		List<T> process(List<T> commands, BindingResult errors);
	}

}
