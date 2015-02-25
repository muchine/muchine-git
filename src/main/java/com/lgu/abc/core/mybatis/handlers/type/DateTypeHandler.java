package com.lgu.abc.core.mybatis.handlers.type;

import java.util.Date;

import org.apache.ibatis.type.MappedTypes;

import com.lgu.abc.core.support.formatter.DateFormatter;

@MappedTypes(Date.class)
public class DateTypeHandler extends SpringFormatterTypeHandler<Date> {

	private static final String format = "yyyyMMddHHmmss";
	
	public static final DateFormatter formatter = new DateFormatter(format);
	
	public DateTypeHandler() {
		super(formatter, null);
	}
	
}
