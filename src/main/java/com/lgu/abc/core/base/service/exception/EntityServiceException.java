package com.lgu.abc.core.base.service.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Exception occured in service layer. When the exception occurs, send notification to administrator using channels like sms
 * 
 * @author sejoon
 *
 */
@SuppressWarnings("serial")
public class EntityServiceException extends RuntimeException {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	public EntityServiceException(Throwable e) {
		super(e);
		
		logger.error(e.getMessage());
		sendNotification();
	}

	public EntityServiceException(String message) {
		super(message);
		
		logger.error(message);
		sendNotification();
	}
	
	private void sendNotification() {
		// TODO send notification using sms, mail, etc. to administrators
	}

}
