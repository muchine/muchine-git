package com.lgu.abc.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Reconfigure InternalResourceViewResolver to protect threads from falling into an infinite loop when adding static attributes
 * to a caching jsp view object. 
 * @author Sejoon
 *
 */
@Component
public class InternalResourceViewResolverConfigurator {

	@Autowired
	private InternalResourceViewResolverConfigurator(InternalResourceViewResolver resolver) {
		resolver.setCache(true);
	}
	
}
