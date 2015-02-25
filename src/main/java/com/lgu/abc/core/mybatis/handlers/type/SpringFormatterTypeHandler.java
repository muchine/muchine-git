package com.lgu.abc.core.mybatis.handlers.type;

import java.text.ParseException;
import java.util.Locale;

import org.apache.ibatis.type.TypeHandler;
import org.springframework.format.Formatter;

public abstract class SpringFormatterTypeHandler<T> extends AbstractTypeHandler<T> implements TypeHandler<T> {

	private final Formatter<T> formatter;
	
	private final Locale locale;
	
	protected SpringFormatterTypeHandler(Formatter<T> formatter, Locale locale) {
		this.formatter = formatter;
		this.locale = locale;
	}
	
	@Override
	protected final T parse(String text) throws ParseException {
		return formatter.parse(text, locale);	
	}

	@Override
	protected final String print(T object) {
		return formatter.print(object, locale);
	}	

}
