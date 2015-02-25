package com.lgu.abc.core.common.infra.notification.code;

import com.lgu.abc.core.common.code.vo.ClassId;
import com.lgu.abc.core.common.vo.Code;

public class NotificationChannelCode extends Code {
	public static final ClassId CLASS = new ClassId("99006");
	
	public static final String MAIL		= "01";
	public static final String SMS		= "02";
	public static final String NOTE		= "03";
	public static final String MESSENGER	= "04";
	public static final String WEB		= "05";
	public static final String PUSH		= "06";
		
	public NotificationChannelCode() {
		this.classId = CLASS;
	}
	
	public NotificationChannelCode(String code) {
		this();
		setCode(code);
	}
}
