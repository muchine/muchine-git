package com.lgu.abc.core.common.configuration;

import org.springframework.core.env.Environment;

public class FormMailSend {
	private static final String ADDRESS		= "form.mail.send.address";
	private static final String IMG			= "form.mail.image.url";
	private static final String FAQ			= "form.mail.faq.url";
	private static final String CALLBACK	= "form.mail.callback.number";
	private static final String OFFICE_IP	= "form.mail.office.ip"; 
	private static final String SMTP_IP	    = "form.mail.smtp.ip"; 
	private static final String MAIL_IP	    = "form.mail.mail.ip"; 
	
	private final Environment env;
	
	public FormMailSend(Environment env){
		this.env = env;
	}
	
	public String address(){
		return env.getProperty(ADDRESS);
	}
	
	public String img(){
		return env.getProperty(IMG);
	}
	
	public String fag(){
		return env.getProperty(FAQ);
	}
	
	public String callback(){
		return env.getProperty(CALLBACK);
	}
	
	public String officeIp(){
		return env.getProperty(OFFICE_IP);
	}
	
	public String smtpIp(){
		return env.getProperty(SMTP_IP);
	}
	
	public String mailIp(){
		return env.getProperty(MAIL_IP);
	}
}
