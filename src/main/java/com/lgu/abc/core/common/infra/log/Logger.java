package com.lgu.abc.core.common.infra.log;

import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.common.infra.log.repo.LogRepository;
import com.lgu.abc.core.prototype.org.user.User;

public class Logger{

	private final org.apache.commons.logging.Log logger;
	
	private final Class<?> type;
	
	private LogRepository repository;
	
	private boolean writable = false;
	
	public Logger(Class<?> type) {
		this.logger = LogFactory.getLog(type);
		this.type = type;
	}
	
	Logger(Class<?> type, LogRepository repository, boolean writable) {
		this(type);
		this.repository = repository;
		this.writable = writable;
	}
	
	public void write(Object userId, String className, String methodName, String message) {
		if (writable) repository.create(new Log(userId == null ? "unknown" : userId.toString(), className, methodName, message));
		
		StringBuilder builder = new StringBuilder();
		builder
			.append(className).append(".").append(methodName)
			.append("(User ID[").append(userId).append("]) : ").append(message);
		
		logger.info(builder.toString());
	}
	
	public <T> void write(T entity, String methodName, String message) {
		if (!(entity instanceof BaseEntity)) logger.info(message(entity, message));
		
		final BaseEntity casted = (BaseEntity) entity;
		final User actor = casted.getActor();
		write(actor == null ? "Unknown" : actor.getId(), type.getSimpleName(), methodName, message(entity, message));
	}
	
	public void trace(Object object) {
		logger.trace(message(object));
	}
	
	public void debug(Object object) {
		logger.debug(message(object));
	}
	
	public void info(Object object) {
		logger.info(message(object));
	}
	
	public void warn(Object object) {
		logger.warn(message(object));
	}
	
	public void error(Object object) {
		logger.error(message(object));
	}
	
	public void fatal(Object object) {
		logger.fatal(message(object));
	}
	
	public <T> void trace(T entity, String message) {
		logger.trace(this.message(entity, message));
	}
	
	public <T> void debug(T entity, String message) {
		logger.debug(this.message(entity, message));
	}
	
	public <T> void info(T entity, String message) {
		logger.info(this.message(entity, message));
	}
	
	public <T> void warn(T entity, String message) {
		logger.warn(this.message(entity, message));
	}
	
	public <T> void error(T entity, String message) {
		logger.error(this.message(entity, message));
	}
	
	public <T> void error(T entity, Exception e) {
		logger.error(this.message(entity, e.getMessage()));
	}
	
	public <T> void fatal(T entity, String message) {
		logger.fatal(this.message(entity, message));
	}
	
	private String message(Object object) {
		return object == null ? "null" : object.toString();
	}

	private <T> String message(T entity, String message) {
		if (entity == null) return "entity is null.";
		
		String actor = "Unknown";
		
		if (entity instanceof BaseEntity) {
			BaseEntity casted = (BaseEntity) entity;
			
			if (casted.getActor() != null)
				actor = casted.getActor().toString();
		}
		
		return actor + "|" + message;		
	}
	
}
