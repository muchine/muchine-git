package com.lgu.abc.core.web.interceptor.security.ip;

public interface IpFilterPolicyLocator {

	IpFilterPolicy find(String domain);
	
}
