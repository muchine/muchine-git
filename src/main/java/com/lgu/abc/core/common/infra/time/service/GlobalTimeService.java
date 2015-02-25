package com.lgu.abc.core.common.infra.time.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GlobalTimeService {

	public static String getTimeStamp() {
		return String.valueOf(new Date().getTime());
	}
	
	public static String getGMTString() {
		SimpleDateFormat format = new SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(GlobalTimeService.getGMTString());
	}

}
