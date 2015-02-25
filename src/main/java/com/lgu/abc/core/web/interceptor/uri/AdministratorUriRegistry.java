package com.lgu.abc.core.web.interceptor.uri;


public class AdministratorUriRegistry {

	private static final String[] uris = new String[] {
		"/cnfg/corp",
		"/org/company/option",
		"/option/company"
	};
	
	public static boolean contains(String compared) {
		for (String uri : uris) {
			if (compared.contains(uri)) return true;
		}
		
		return false;
	}
	
}
