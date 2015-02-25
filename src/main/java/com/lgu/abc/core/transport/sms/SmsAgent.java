package com.lgu.abc.core.transport.sms;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.configuration.AbcConfig;

@Component
public class SmsAgent {
	
	private static final String namespace = SmsAgent.class.getCanonicalName();
	
	@Autowired
	private SqlSessionTemplate template;

	@Autowired
	private AbcConfig configuration;
	
	public void send(String content) {
		String[] contacts = configuration.system().contact().mobiles();
		send(contacts, content);
	}
	
	public void send(String receiver, String content) {
		template.insert(namespace + ".create", new SmsMessage(receiver, content));
	}
	
	public void send(String[] receivers, String content) {
		for (String receiver : receivers) {
			send(receiver, content);
		}
	}
		
}
