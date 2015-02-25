package com.lgu.abc.core.web.mobile;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.lgu.abc.core.web.security.IntegrationIpFilter;
import com.lgu.abc.core.web.session.SessionManager;
import com.u2ware.springfield.controller.EntityHandler;

@Component
public class MobileViewResolver {
	
	public String resolve(Model model, Object object)  {
		IntegrationIpFilter.validate(SessionManager.getCurrentRequest());
		
		model.addAttribute(EntityHandler.MODEL_ENTITY, object);
		return "result.xml";
	}
	
	public String resolve(MobileView convertable) {
		IntegrationIpFilter.validate(SessionManager.getCurrentRequest());
		return convertable.toXML();
	}
	
}
