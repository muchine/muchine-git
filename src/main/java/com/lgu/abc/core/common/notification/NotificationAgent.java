package com.lgu.abc.core.common.notification;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.common.notification.domain.NotificationBean;
import com.lgu.abc.core.common.notification.domain.NotificationRequest;
import com.lgu.abc.core.common.notification.domain.NotificationResponse;

@Component
public class NotificationAgent {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	private final RestTemplate template = new RestTemplate();
	
	@Autowired
	private AbcConfig configuration;
	
	public NotificationResponse sendNotification(NotificationBean bean) {
		try {
			if (bean.getTargetDiv() == null) {
				bean.setTargetDiv("99");
			}
				
			NotificationRequest notification = new NotificationRequest(bean);
			
			String request = mapper.writeValueAsString(notification);
			logger.debug("notification request: " + request);
			
			return sendToPNS(request);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private NotificationResponse sendToPNS(String request) {
		NotificationResponse response = null;
		
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("data", request);

			String url = configuration.getEnvironment().getProperty("notification.server.url");
			ResponseEntity<String> result = template.postForEntity(url, param, String.class);

			response = mapper.readValue(result.getBody(), NotificationResponse.class);			
			logger.debug("response: " + response.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}

		return response;
	}	

}