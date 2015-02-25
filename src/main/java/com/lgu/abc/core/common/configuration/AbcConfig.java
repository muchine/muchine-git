package com.lgu.abc.core.common.configuration;

import lombok.Getter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.configuration.system.SystemEnvironment;
import com.lgu.abc.core.common.configuration.upload.Upload;

/**
 * Desc : ABC Config
 * FileName : AbcConfig.java
 * ClassName : AbcConfig
 * Date : 2013. 1. 14.
 * Author : leechanwoo
 */
@Component
@PropertySource({"classpath:prop/abc.properties", "classpath:prop/application-context.properties"})
public class AbcConfig {

	private static AbcConfig instance;
	
	@Getter
	private final Environment environment;

	private SystemEnvironment system;
	
	private Upload upload;
	
	private Authentication authentication;
	
	private UBoxConfiguration ubox;
	
	private Messenger messenger;
	
	private FormMailSend formMailSend;
	
	@Getter
	private final boolean batchEnabled;
	
	@Getter
	private final String wasId;
	
	@Autowired 
	private AbcConfig(Environment environment) {
		this.environment = environment;
		this.system = new SystemEnvironment(environment);
		this.upload = new Upload(environment);
		this.authentication = new Authentication(environment);
		this.ubox = new UBoxConfiguration(environment);
		this.messenger = new Messenger(environment);
		this.formMailSend = new FormMailSend(environment);
		
		initialize();
		
		String enabled = System.getProperty("abc.batch");
		batchEnabled = !StringUtils.isEmpty(enabled) && Boolean.parseBoolean(enabled);
		
		wasId = System.getProperty("abc.wasId");
	}
	
	public synchronized void initialize() {
		instance = this;
	}
	
	public static AbcConfig instance() {
		return instance;
	}
	
	public String getProperty(String key) {
		return getEnvironment().getProperty(key);
	}
	
	public SystemEnvironment system() {
		return system;
	}
	
	public Upload upload() {
		return upload;
	}
	
	public Authentication authentication() {
		return authentication;
	}
	
	public UBoxConfiguration ubox() {
		return ubox;
	}
	
	public Messenger messenger() {
		return messenger;
	}
	
	public FormMailSend formMailSend(){
		return formMailSend;
	}
	
}
