package com.lgu.abc.core.web.interceptor.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.module.GlobalModuleRegistry;
import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.ModuleComparator;
import com.lgu.abc.core.module.ModuleUtils;
import com.lgu.abc.core.module.exception.ModuleNotFoundException;
import com.lgu.abc.core.module.view.ModuleViewSet;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.interceptor.InterceptorFilter;
import com.lgu.abc.core.web.session.SessionManager;
import com.lgu.abc.core.web.support.WebUtils;

@Component
public final class ModuleFilter implements InterceptorFilter {
	
	public static final String ATTRIBUTE_MYWORK = "myworkModules";
	public static final String ATTRIBUTE_SUPPORT = "supportModules";
	public static final String ATTRIBUTE_WORKSPACE_ENABLED = "workspaceEnabled";
	
	@Autowired
	private GlobalModuleRegistry registry;
	
	@Autowired
	private ModuleFilter(ComponentInterceptor interceptor) {
		interceptor.add(this);
	}
	
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final User user = SessionManager.getSession(request);
		if (!validateUri(request, user)) {
			response.setStatus(HttpStatus.SC_BAD_REQUEST);
			return false;
		}
		
		List<Module> myworks = new ArrayList<Module>();
		List<Module> supports = new ArrayList<Module>();
		
		for (Module module : registry.all(user.getCompany())) {
			switch(module.type()) {
				case MYWORK : myworks.add(module); break;
				case SUPPORT : supports.add(module); break;
				default:
			}
		}
		
		request.setAttribute(ATTRIBUTE_MYWORK, modules(user, myworks));
		request.setAttribute(ATTRIBUTE_SUPPORT, modules(user, supports));
		request.setAttribute(ATTRIBUTE_WORKSPACE_ENABLED, isWorkspaceEnabled(user.getCompany()));
		
		return true;
	}
	
	@Override
	public int order() {
		return 1;
	}
		
	private boolean validateUri(HttpServletRequest request, User user) {
		final String uri = WebUtils.uri(request);
		final List<Module> modules = registry.all(user.getCompany());
		
		for (Module module : modules) {
			if (uri.startsWith(module.home())) return ModuleUtils.isEnabled(module);
		}
		
		return true;
	}

	private ModuleViewSet modules(User user, List<Module> modules) {
		Collections.sort(modules, ModuleComparator.instance());		
		return new ModuleViewSet(modules, user);
	}
	
	private boolean isWorkspaceEnabled(Company company) {
		try {
			Module module = registry.find(company, "workspace");
			return ModuleUtils.isEnabled(module);	
		} catch (ModuleNotFoundException e) {
			return false;
		}		
	}
		
}
