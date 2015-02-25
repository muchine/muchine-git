package com.lgu.abc.core.web.view.format;

import java.util.Date;

public interface Formattable {

	String format(Date date);
	
	String date(Date date);
	
	String time(Date date);
	
}
