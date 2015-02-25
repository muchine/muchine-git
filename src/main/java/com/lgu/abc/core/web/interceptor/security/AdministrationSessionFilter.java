package com.lgu.abc.core.web.interceptor.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.security.role.company.CompanyAdministrator;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.interceptor.InterceptorFilter;
import com.lgu.abc.core.web.interceptor.uri.AdministratorUriRegistry;
import com.lgu.abc.core.web.session.SessionManager;
import com.lgu.abc.core.web.support.WebUtils;

@Component
public final class AdministrationSessionFilter implements InterceptorFilter {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private AdministrationSessionFilter(SecurityInterceptor interceptor) {
		interceptor.add(this);
	}
	
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.trace("filtering administration session...");
		if (!AdministratorUriRegistry.contains(WebUtils.uri(request))) return true;
		
		User session = SessionManager.getSession(request);
		if (session.hasRole(new CompanyAdministrator(session.getCompany().getId()))) return true;
		
		if (WebUtils.isAjax(request))
			response.setStatus(HttpStatus.SC_FORBIDDEN);
		else
			response.sendRedirect("/");
		
		return false;
	}
	
	@Override
	public int order() {
		return 5;
	}

}
