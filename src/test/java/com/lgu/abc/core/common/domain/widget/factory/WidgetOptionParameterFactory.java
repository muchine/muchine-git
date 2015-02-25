package com.lgu.abc.core.common.domain.widget.factory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lgu.abc.test.common.base.factory.AbstractParameterFactory;

@Component
public class WidgetOptionParameterFactory extends AbstractParameterFactory {

	@Override
	public Map<String, String> getCreationParameters() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("category", "main");
		params.put("type", "profile");
		params.put("position", "left");
		params.put("order", "1");
		
		return params;
	}

	@Override
	public Map<String, String> getUpdateParameters() {
		Map<String, String> params = getCreationParameters();
		
		params.put("position", "center");
		params.put("order", "2");
		
		return params;
	}
	
	@Override
	public Map<String, String> getDeletionParameters() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("category", "main");
		params.put("type", "profile");
		
		return params;
	}

	@Override
	public Map<String, String> getQueryParameters() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("category", getCreationParameters().get("category"));
		
		return params;
	}
	
	public Map<String, String> getAnotherCreationParameters() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("category", "mywork");
		params.put("type", "approval");
		params.put("itemId", "2000");
		params.put("position", "right");
		params.put("order", "3");
		
		return params;
	}
	
}
