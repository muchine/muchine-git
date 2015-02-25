package com.lgu.abc.core.common.configuration.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.env.Environment;

import com.lgu.abc.core.common.domain.security.IpAccess;

public class IntegrationEnvironment {

	private static final String IP = "system.integration.ip";
	
	private final List<IpAccess> ipAccesses;
	
	public IntegrationEnvironment(Environment env) {
		String ips = env.getProperty(IP);
		
		ipAccesses = new ArrayList<IpAccess>();
		for (String ip : ips.split(",")) ipAccesses.add(new IpAccess(ip));
	}
	
	public List<IpAccess> ipAccesses() {
		return ipAccesses;
	}
	
}
