package com.lgu.abc.core.plugin.session.init;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.module.GlobalModuleRegistry;
import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.ModuleUtils;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.session.SessionManager;

@Component
public class GlobalSessionInitializer {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private GlobalModuleRegistry registry;
	
	private final List<SessionInitializable> initializables = new ArrayList<SessionInitializable>();
	
	public synchronized void add(SessionInitializable initializable) {
		Validate.notNull(initializable);
		
		logger.info("add session initializer: " + initializable.getClass());
		initializables.add(initializable);
	}
	
	public void initialize(User user) {
		HttpSession session = sessionManager.getCurrentSession();
		for (SessionInitializable initializable : initializables) {
			logger.debug("initialize custom session... " + initializable.getClass());
			if (!isModuleEnabled(user, initializable)) continue;
			
			SessionAttribute attribute = initializable.initialize(user);
			session.setAttribute(attribute.getName(), attribute.getObject());
		}
		
		logger.debug("session initialization done...");
	}
	
	private boolean isModuleEnabled(User user, SessionInitializable initializable) {
		Module module = registry.find(user.getCompany(), initializable.moduleId());
		return ModuleUtils.isEnabled(module);
	}
	
}
