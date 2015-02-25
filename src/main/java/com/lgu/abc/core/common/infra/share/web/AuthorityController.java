package com.lgu.abc.core.common.infra.share.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgu.abc.core.base.controller.command.MacroCommandController;
import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.AuthorityQuery;
import com.lgu.abc.core.common.infra.share.service.AuthorityService;
import com.lgu.abc.core.common.infra.share.web.command.AuthorityShowCommand;
import com.lgu.abc.core.common.infra.share.web.command.AuthorityShowCommandList;
import com.lgu.abc.core.support.annotation.Operations;
import com.lgu.abc.core.web.controller.ControllerAuxiliary;

@Controller
@RequestMapping(AuthorityMetamodel.BASE_URL)
@Operations(type={
	OperationType.CREATE_FORM,
	OperationType.CREATE,
	OperationType.UPDATE_FORM,
	OperationType.UPDATE,
	OperationType.DELETE,
	OperationType.READ,
	OperationType.MULTI_CREATE,
	OperationType.MULTI_UPDATE,
	OperationType.MULTI_DELETE
})
public class AuthorityController extends MacroCommandController<Authority, AuthorityQuery, AuthorityList> {

	@Autowired
	public AuthorityController(AuthorityService service) {
		/*
		 * TODO Need to implement specific authorizer
		 */
		super(service, new AuthorityMetamodel(), new ControllerAuxiliary<Authority, AuthorityQuery>());
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/show")
	public String show(Model model, @ModelAttribute AuthorityShowCommand command, BindingResult errors, 
			@PathVariable("id") String id) throws Exception {
		command.setId(id);
		return doUpdateWithCommand(model, command, errors);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/list/show")
	public String show(Model model, @ModelAttribute AuthorityShowCommandList command, BindingResult errors) throws Exception {
		return doUpdateWithCommand(model, command.getEntities(), errors);
	}

}
