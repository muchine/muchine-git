package com.lgu.abc.core.base.controller.command;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgu.abc.core.base.controller.BaseController;
import com.lgu.abc.core.base.controller.Executable;
import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.service.DomainService;
import com.lgu.abc.core.common.interfaces.Command;
import com.lgu.abc.core.common.interfaces.Rearrangeable;
import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.exception.http.MethodNotAllowedException;
import com.lgu.abc.core.exception.http.ResourceNotFoundException;
import com.lgu.abc.core.web.controller.ControllerAuxiliary;
import com.lgu.abc.core.web.view.command.RearrangeCommand;
import com.u2ware.springfield.controller.HandlerMetamodel;
import com.u2ware.springfield.service.EntityService;

public abstract class CommandController<T extends RootEntity, Q extends BaseEntity> extends BaseController<T, Q> {

	protected final String MESSAGE_PREFIX = "view.";
	
	public CommandController(EntityService<T, Q> service, HandlerMetamodel<T, Q> metamodel, ControllerAuxiliary<T, Q> auxiliary) {
		super(service, metamodel, auxiliary);
	}
	
	@Override
	public String doCreate(Model model, @ModelAttribute(MODEL_ENTITY) @Validated T command, BindingResult errors) throws Exception {
		return doCreateWithCommand(model, command, errors);
	}

	@Override
	public String doUpdate(Model model, @ModelAttribute(MODEL_ENTITY) @Validated T command, BindingResult errors) throws Exception {
		return doUpdateWithCommand(model, command, errors);
	}
	
	@Override
	public String doDelete(Model model, @ModelAttribute(MODEL_ENTITY) T command, BindingResult errors) throws Exception {
		return doDeleteWithCommand(model, command, errors);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/rearrange")
	public String rearrange(Model model, @ModelAttribute RearrangeCommand command, BindingResult errors) throws Exception {
		final T entity = createEntityObject();
		if (!(entity instanceof Rearrangeable)) throw new MethodNotAllowedException();
		
		command.setEntity(entity);
		final int to = command.getTo();
		
		return doUpdateWithCommand(model, command, errors, new Executable<T, Q>() {

			@Override
			public T execute(EntityService<T, Q> service, T entity) {
				((DomainService<T, Q>) service).rearrange(entity, to);
				entity.success();
				return entity;
			}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	protected final String doCreateWithCommand(Model model, Command command, BindingResult errors) throws Exception {
		return super.doCreate(model, (T) command.create(), errors);
	}
	
	@SuppressWarnings("unchecked")
	protected final String doExecuteWithCommand(Model model, Command command, BindingResult errors, Executable<T, Q> executable) 
			throws Exception {
		return super.doUpdate(model, (T) command.create(), errors, executable);
	}
	
	protected final String doUpdateWithCommand(Model model, Command command, BindingResult errors) throws Exception {
		return super.doUpdate(model, update(command, errors), errors);
	}
	
	protected final String doUpdateWithCommand(Model model, Command command, BindingResult errors, Executable<T, Q> executable) 
			throws Exception {
		return super.doUpdate(model, update(command, errors), errors, executable);
	}
	
	protected final String doDeleteWithCommand(Model model, Command command, BindingResult errors) throws Exception {
		return super.doDelete(model, delete(command, errors), errors);
	}
		
	@SuppressWarnings("unchecked")
	protected final T update(Command command, BindingResult errors) {
		T entity = read((T) command.read());
		if (entity == null) throw new ResourceNotFoundException();
		
		try {
			return (T) command.update(entity);	
		} catch(CoreException e) {
			return handleEntityConversionException(e, errors);
		}		
	}
	
	@SuppressWarnings("unchecked")
	protected final T delete(Command command, BindingResult errors) {
		T entity = read((T) command.read());		
		if (entity == null) throw new ResourceNotFoundException();
				
		try {
			return (T) command.delete(entity);
		} catch(CoreException e) {
			return handleEntityConversionException(e, errors);
		}
	}
	
	private T read(T command) {
		supporter.prepareRead(command);
		T read = getService().read(command);
		
		logger.debug("found entity: " + read);
		return read;
	}
	
	private T handleEntityConversionException(CoreException e, BindingResult errors) {
		T entity = this.createEntityObject();
		entity.fail(e);
		errors.reject("exception", "exception occured.");
		
		return entity;
	}

}
