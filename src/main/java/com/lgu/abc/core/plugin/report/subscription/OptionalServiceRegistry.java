package com.lgu.abc.core.plugin.report.subscription;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;

@Component
public class OptionalServiceRegistry {

	private static final Set<OptionalService> services = new HashSet<OptionalService>();
	
	public synchronized void add(OptionalService service) {
		PluggableUtils.add(service, services);
	}
	
	public static Set<OptionalService> all() {
		return PluggableUtils.all(services);
	}
	
}
