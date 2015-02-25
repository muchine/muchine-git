package com.lgu.abc.core.base.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.query.ResolvableQuery;
import com.lgu.abc.core.prototype.workspace.Workspace;
import com.lgu.abc.core.prototype.workspace.service.WorkspaceService;
import com.lgu.abc.core.web.controller.ControllerAuxiliary;
import com.u2ware.springfield.controller.HandlerMetamodel;
import com.u2ware.springfield.domain.EntityPageable;
import com.u2ware.springfield.service.EntityService;

public abstract class RichCommandController<T extends RootEntity, Q extends ResolvableQuery> extends CommandController<T, Q> {

	@Autowired
	private WorkspaceService workspaceService;

	public RichCommandController(EntityService<T, Q> service, HandlerMetamodel<T, Q> metamodel, ControllerAuxiliary<T, Q> auxiliary) {
		super(service, metamodel, auxiliary);
	}
	
	/*
	 * Workspace Methods
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/workspace/{id}/new")
	public String createWorkspaceEntity(Model model, @ModelAttribute(MODEL_ENTITY) @Validated T entity, BindingResult errors, 
			@PathVariable("id") String workspaceId) throws Exception {
		Workspace workspace = new Workspace();
		workspace.setId(workspaceId);
		
		Workspace found = workspaceService.read(workspace);
		entity.setOwnable(found);
		
		return create(model, entity, errors);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/workspace/{id}")
	public String findWorkspaceEntities(EntityPageable pageable, Model model, @ModelAttribute(MODEL_QUERY) Q query, BindingResult errors, 
			@PathVariable("id") String workspaceId) throws Exception {
		query.findByWorkspaceId(workspaceId);
		return find(pageable, model, query, errors);
	}
	
	/*
	 * Company Methods
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/company/new")
	public String createCompanyEntities(Model model, @ModelAttribute(MODEL_ENTITY) @Validated T entity, BindingResult errors) throws Exception {
		entity.setOwnable(actor().getCompany());
		return create(model, entity, errors);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/company")
	public String findCompanyEntities(EntityPageable pageable, Model model, @ModelAttribute(MODEL_QUERY) Q query, BindingResult errors) throws Exception {
		query.findByCompanyId(actor().getCompany().getId());
		return find(pageable, model, query, errors);
	}

}
