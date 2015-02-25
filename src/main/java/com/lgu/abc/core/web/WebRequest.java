package com.lgu.abc.core.web;

import lombok.Data;

import org.springframework.web.bind.annotation.RequestMethod;

public @Data class WebRequest {

	private final RequestMethod method;
	
	private final String url;
	
	public WebRequest(RequestMethod method, String url) {
		this.method = method;
		this.url = url;
	}
	
	public boolean isCreating() {
		return RequestMethod.POST.equals(method) && url.contains("new");
	}
	
	public boolean isUpdating() {
		return RequestMethod.PUT.equals(method);
	}
	
}
