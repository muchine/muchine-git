package com.lgu.abc.core.base.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgu.abc.core.base.OwnableComparator;
import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.query.AuthorizableQuery;
import com.lgu.abc.core.base.factory.QueryResultFactory;
import com.lgu.abc.core.base.operation.OperationRegistry;
import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.base.service.DomainService;
import com.lgu.abc.core.common.infra.log.Logger;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Protectable;
import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.exception.http.MethodNotAllowedException;
import com.lgu.abc.core.exception.http.ResourceNotFoundException;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.annotation.Operations;
import com.lgu.abc.core.support.id.IdGenerator;
import com.lgu.abc.core.web.WebRequest;
import com.lgu.abc.core.web.controller.ControllerAuxiliary;
import com.lgu.abc.core.web.controller.ControllerUtil;
import com.lgu.abc.core.web.security.authorizer.Authorizer;
import com.lgu.abc.core.web.security.filter.Filter;
import com.lgu.abc.core.web.security.filter.type.DefaultFilter;
import com.lgu.abc.core.web.session.SessionManager;
import com.lgu.ubox.util.Debugger;
import com.u2ware.springfield.controller.EntityHandler;
import com.u2ware.springfield.controller.HandlerMetamodel;
import com.u2ware.springfield.domain.EntityPageable;
import com.u2ware.springfield.domain.ValidationRejectableException;
import com.u2ware.springfield.service.EntityService;

/**
 * Handles default CRUD requests.
 * 
 * @author sejoon
 * 
 * @param <T>
 * @param <Q>
 */
public abstract class BaseController<T extends RootEntity, Q extends BaseEntity> extends EntityHandler<T, Q> {

	private final String ACTION_CONTEXT_ATTRIBUTE = "actionContext";
	
	protected final Logger logger = new Logger(getClass());
	
	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private IdGenerator sequenceManager;

	private final OperationRegistry registry = new OperationRegistry();
	
	private final Class<Q> queryClass;
	
	@Getter
	private final String baseUrl = ControllerUtil.getBaseUrl(this);
	
	protected final ControllerSupporter<T, Q> supporter;
	
	public BaseController(EntityService<T, Q> service, HandlerMetamodel<T, Q> metamodel, Authorizer<T, Q> authorizer) {
		this(service, metamodel, authorizer, new DefaultFilter<T, Q>());
	}
	
	public BaseController(EntityService<T, Q> service, HandlerMetamodel<T, Q> metamodel, Authorizer<T, Q> authorizer, Filter<T, Q> filter) {
		super(service, metamodel);
		
		queryClass = getQueryClass();
		supporter = new ControllerSupporter<T, Q>(this, authorizer, filter);
		
		setSupportOperations();
	}
	
	public BaseController(EntityService<T, Q> service, HandlerMetamodel<T, Q> metamodel, ControllerAuxiliary<T, Q> auxiliary) {
		this(service, metamodel, auxiliary.getAuthorizer(), auxiliary.getFilter());
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String home(Model model, @ModelAttribute(MODEL_QUERY) Q query, BindingResult errors) throws Exception {
		if (!canSupport(OperationType.HOME)) throw new ResourceNotFoundException();
		
//		supporter.prepareQuery(query, errors, null);
		model.addAttribute("model_entity", new Object());

		return super.home(model, query, errors);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/find")
	public String findView(Model model) throws Exception {
		return super.resolveViewName(model, "findForm", null, null, null, null);
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "")
	public String findForm(EntityPageable pageable, Model model, @ModelAttribute(MODEL_QUERY) Q query, BindingResult errors) throws Exception {
		if (!canSupport(OperationType.FIND_FORM)) throw new ResourceNotFoundException();
		
		supporter.prepareQuery(query, errors, pageable);

		String view = super.findForm(pageable, model, query, errors);
		supporter.postQuery(query, model, errors);
		
		return view;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, value = "")
	public String find(EntityPageable pageable, Model model, @ModelAttribute(MODEL_QUERY) Q query, BindingResult errors) throws Exception {
		if (!canSupport(OperationType.FIND)) throw new ResourceNotFoundException();
		return doFind(pageable, model, query, errors);
	}
	
	protected String doFind(EntityPageable pageable, Model model, @ModelAttribute(MODEL_QUERY) Q query, BindingResult errors) throws Exception {
		supporter.prepareQuery(query, errors, pageable);

		String view = super.find(pageable, model, query, errors);
		supporter.postQuery(query, model, errors);
		
		return view;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/count")
	@ResponseBody
	public Long count(Model model, @ModelAttribute(MODEL_QUERY) Q query, BindingResult errors) throws Exception {
		supporter.prepareQuery(query, errors, null);
		return ((DomainService<T, Q>) getService()).count(query);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/writable")
	public String findWritable(EntityPageable pageable, Model model, @ModelAttribute(MODEL_QUERY) Q query, BindingResult errors) throws Exception {
		if (!(query instanceof AuthorizableQuery)) throw new ResourceNotFoundException();
		
		((AuthorizableQuery) query).findAllWritable();
		return findAuthorized(pageable, model, query, errors);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/manageable")
	public String findManageable(EntityPageable pageable, Model model, @ModelAttribute(MODEL_QUERY) Q query, BindingResult errors) throws Exception {
		if (!(query instanceof AuthorizableQuery)) throw new ResourceNotFoundException();
		
		((AuthorizableQuery) query).findAllManageable();
		return findAuthorized(pageable, model, query, errors);
	}
	
	private String findAuthorized(EntityPageable pageable, Model model, Q query, BindingResult errors) throws Exception {
		String view = find(pageable, model, query, errors);
		
		@SuppressWarnings("unchecked")
		Iterable<T> found = (Iterable<T>) model.asMap().get(MODEL_QUERY_RESULT);
		List<T> result = new ArrayList<T>();
		for (T entity : found) {
			if (((AuthorizableQuery) query).canQuery(entity)) result.add(entity);
		}
		
		Collections.sort(result, new OwnableComparator(actor()));
		
		model.addAttribute(MODEL_QUERY_RESULT, QueryResultFactory.create(result, found, pageable));		
		return view;
	}
	
	@Override
	@RequestMapping(method = RequestMethod.GET, value = "/command_id")
	public String read(Model model, @ModelAttribute(MODEL_ENTITY) T entity, BindingResult errors) throws Exception {
		if (!canSupport(OperationType.READ)) throw new ResourceNotFoundException();
		return doRead(model, entity, errors);
	}
	
	protected String doRead(Model model, @ModelAttribute(MODEL_ENTITY) T entity, BindingResult errors) throws Exception {
		supporter.prepareRead(entity);
		logger.debug("entity read: " + Debugger.entityToString(entity));

		/*
		 * NOTE The weird thing is that validation errors aren't sent to view. For now erase model entity.
		 */
		String view = super.read(model, entity, errors);
		supporter.postRead(model, entity, errors);
		
		return view;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "/new")
	public String createForm(Model model, @ModelAttribute(MODEL_ENTITY) T entity, BindingResult errors) throws Exception {
		if (!canSupport(OperationType.CREATE_FORM)) throw new ResourceNotFoundException();
		
		supporter.prepareRead(entity);
		model.addAttribute(ACTION_CONTEXT_ATTRIBUTE, new WebRequest(RequestMethod.POST, getBaseUrl() + "/new.json"));
		return super.createForm(model, entity, errors);
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, value = "/new")
	public String create(Model model, @ModelAttribute(MODEL_ENTITY) @Validated T entity, BindingResult errors) throws Exception {
		if (!canSupport(OperationType.CREATE)) throw new MethodNotAllowedException();
		return doCreate(model, entity, errors);
	}
	
	protected String doCreate(Model model, T entity, BindingResult errors) throws Exception {
		T prepared = supporter.prepareCreation(entity, errors);
		
		try {
			return super.create(model, prepared, errors);	
		}
		catch (CoreException e) {
			prepared.fail(e);
			return resolveViewName(model, "createForm", prepared, null, null, null);
		}
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "/command_id/edit")
	public String updateForm(Model model, @ModelAttribute(MODEL_ENTITY) T entity, BindingResult errors) throws Exception {
		if (!canSupport(OperationType.UPDATE_FORM)) throw new ResourceNotFoundException();
		
		supporter.prepareRead(entity);
		logger.debug("entity updateForm:\n" + "id: " + entity.getId() + "\n");
		logger.trace("entity updateForm: " + entity);

		model.addAttribute(ACTION_CONTEXT_ATTRIBUTE, new WebRequest(RequestMethod.PUT, getBaseUrl() + "/" + entity.getId() + "/edit.json"));
		String view = super.updateForm(model, entity, errors);
		supporter.postRead(model, entity, errors);
		
		return view;
	}
	
	@Override
	@RequestMapping(method = RequestMethod.PUT, value = "/command_id/edit")
	public String update(Model model, @ModelAttribute(MODEL_ENTITY) @Validated T entity, BindingResult errors) throws Exception {
		if (!canSupport(OperationType.UPDATE)) throw new MethodNotAllowedException();
		return doUpdate(model, entity, errors);
	}
	
	protected String doUpdate(Model model, T entity, BindingResult errors) throws Exception {
		return doUpdate(model, entity, errors, new Executable<T, Q>() {

			@Override
			public T execute(EntityService<T, Q> service, T entity) {
				return service.update(entity);
			}
			
		});
	}
	
	protected final String doUpdate(Model model, T entity, BindingResult errors, Executable<T, Q> updatable) throws Exception {
		final T prepared = supporter.prepareUpdate(entity, errors);
		
		if(errors.hasErrors()) {
			super.resolveValidation(errors);
			return resolveViewName(model, "updateForm", prepared, null, null, null);
		}
		
		try{
			T updated = updatable.execute(getService(), prepared);
			return resolveViewName(model, "update", updated, null, null, null);			
		}
		catch(CoreException e) {
			prepared.fail(e);
			return resolveViewName(model, "updateForm", prepared, null, null, null);
		}
		catch(ValidationRejectableException e){
			super.resolveValidation(errors, e);
			return resolveViewName(model, "updateForm", prepared, null, null, null);
		}
	}

	@Override
	@RequestMapping(method = RequestMethod.DELETE, value = "/command_id/edit")
	public String delete(Model model, @ModelAttribute(MODEL_ENTITY) T entity, BindingResult errors) throws Exception {
		if (!canSupport(OperationType.DELETE)) throw new MethodNotAllowedException();
		return doDelete(model, entity, errors);
	}
	
	protected String doDelete(Model model, T entity, BindingResult errors) throws Exception {
		T prepared = supporter.prepareDeletion(entity, errors);
		
		/*
		 * In springfield, delete method doesn't see validation error but calls service's delete method directly. If an error has been added by Authorizer,
		 * should redirect user to error view instead of calling delete operation.
		 */
		if (errors.hasErrors()) {
			super.resolveValidation(errors);
			return super.resolveViewName(model, "read", prepared, null, null, null);
		}
		
		try {
			return super.delete(model, prepared, errors);	
		}
		catch (CoreException e) {
			prepared.fail(e);
			return resolveViewName(model, "read", entity, null, null, null);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/privilege.json") 
	@ResponseBody
	public Privilege privilege(@PathVariable("id") String entityId) {
		T entity = createEntityObject();
		entity.setId(entityId);
		
		logger.debug("read privilege for entity id: " + entityId);
		
		T found = getService().read(entity);
		Privilege priv = actor().privilege((Protectable) found);
		
		return priv == null ? new Privilege(AccessLevel.NONE) : priv;
	}

	protected final User actor() {
		return sessionManager.getSession();
	}
	
	SessionManager getSessionManager() {
		return sessionManager;
	}
	
	IdGenerator getSequenceManager() {
		return sequenceManager;
	}

	/*
	 * Operation methods
	 */
	public final List<OperationType> getSupportOperations() {
		return registry.getSupportOperations();
	}

	public final boolean canSupport(OperationType type) {
		return registry.canSupport(type);
	}

	private void setSupportOperations() {
		Operations ops = getClass().getAnnotation(Operations.class);		
		if (ops != null) registry.setSupporOperations(ops.type());
	}
	
	/**
	 * Overrides query class when custom query class is defined. From Springfield, we must define query class via QueryMethod annotation but
	 * this approach is inefficient because Springfield creates service and controller bean respectively for query. Therefore, we define
	 * custom query class and this method returns this query class as model. The important note is that there are two points getting query class 
	 * from HandlerMetamodel. One is parent's createQueryObject method and the other is a method named createMethodLevelRequestMappingValues().
	 * We need to dig more around this method to make sure this query class overriding has no side effect on it.
	 */
	@Override
	@ModelAttribute(MODEL_QUERY)
	public final Q createQueryObject() {
		try {
			Q command = queryClass.newInstance();
			logger.info("@ModelAttribute(" + MODEL_QUERY + "): " + command.getClass());

			return command;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Class<Q> getQueryClass() {
		return (Class<Q>) ControllerUtil.getQueryClass(getClass(), getQueryParameterIndex());
	}
	
	protected int getQueryParameterIndex() {
		return 1;
	}
	
	protected String validateErrors(Model model, T entity, BindingResult errors, String view) throws Exception {
		if(errors.hasErrors()){
			super.resolveValidation(errors);
			return resolveViewName(model, view, entity, null, null, null);
		}
		
		return null;
	}
	
}
