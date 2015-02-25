package com.lgu.abc.core.web.view.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultViewFormatter implements Formattable {

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String TIME_FORMAT = "HH:mm:ss";
	
	protected DefaultViewFormatter() {}
	
	@Override
	public String format(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT + " " + TIME_FORMAT);
		return format.format(date);
	}

	@Override
	public String date(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return format.format(date);
	}

	@Override
	public String time(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
		return format.format(date);
	}

}
