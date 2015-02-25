package com.lgu.abc.core.common.notification.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Desc :
 * FileName : NotiResponseBean.java
 * ClassName : NotiResponseBean
 * Date : 2013. 1. 22.
 * Author : leechanwoo
 */
public class NotificationResponse implements Serializable {
	private static final long serialVersionUID = -8195049070288178382L;
	@Getter @Setter private String code;
	@Getter @Setter private String message;
	
	public static final String OK = "200";
	
	public NotificationResponse() {}
	
	public NotificationResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public boolean isOk() {
		return OK.equals(code);
	}
	
	@Override
	public String toString() {
		return "NotiResponseBean [code=" + code + ", message=" + message + "]";
	}
}
