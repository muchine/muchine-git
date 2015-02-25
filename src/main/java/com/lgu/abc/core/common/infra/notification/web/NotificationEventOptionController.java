package com.lgu.abc.core.common.infra.notification.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgu.abc.core.base.controller.command.MacroCommandController;
import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.common.infra.notification.NotificationEventOption;
import com.lgu.abc.core.common.infra.notification.service.NotificationEventOptionService;
import com.lgu.abc.core.support.annotation.Operations;
import com.lgu.abc.core.web.controller.ControllerAuxiliary;
import com.lgu.abc.core.web.security.authorizer.impl.PrivilegeAuthorizer;

@Controller
@RequestMapping(NotificationEventOptionMetamodel.BASE_URL)
@Operations(type={
	OperationType.UPDATE,
	OperationType.FIND_FORM,
	OperationType.MULTI_UPDATE
})
public class NotificationEventOptionController extends 
		MacroCommandController<NotificationEventOption, NotificationEventOption, NotificationEventOptionList> {

	@Autowired
	public NotificationEventOptionController(NotificationEventOptionService service) {
		super(service, new NotificationEventOptionMetamodel(), new ControllerAuxiliary<NotificationEventOption, NotificationEventOption>(
			new PrivilegeAuthorizer<NotificationEventOption, NotificationEventOption>()
		));
	}

}