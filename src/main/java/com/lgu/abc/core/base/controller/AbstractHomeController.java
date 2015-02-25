package com.lgu.abc.core.base.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.controller.ControllerUtil;
import com.lgu.abc.core.web.session.SessionManager;

public abstract class AbstractHomeController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	private final String PATH;  
	
	public AbstractHomeController() {
		PATH = ControllerUtil.getBaseUrl(this);
	}
	
	protected String view(String view) {
		return PATH + view;
	}
	
	protected User actor() {
		return sessionManager.getSession();
	}
	
	public String getBaseUrl() {
		return ControllerUtil.getBaseUrl(this);
	}
	
}
