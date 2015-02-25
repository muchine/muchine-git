package com.lgu.abc.core.mybatis.handlers.type;

import java.text.ParseException;
import java.util.Locale;

import org.apache.ibatis.type.MappedTypes;

@MappedTypes(Locale.class)
public class LocaleTypeHandler extends AbstractTypeHandler<Locale> {

	private static final String KOREAN		= "ko";
	private static final String ENGLISH		= "en";
	private static final String CHINESE		= "zh";
	private static final String JAPANESE	= "ja";
	
	@Override
	protected String print(Locale object) {
		logger.trace("print locale... locale: " + object);
		
		if (Locale.KOREAN.equals(object)) return KOREAN;
		if (Locale.ENGLISH.equals(object)) return ENGLISH;
		if (Locale.CHINESE.equals(object)) return CHINESE;
		if (Locale.JAPANESE.equals(object)) return JAPANESE;
		
		throw new IllegalArgumentException("invalid locale: " + object);
	}

	@Override
	protected Locale parse(String text) throws ParseException {
		logger.trace("parse to locale... text: " + text);
		
		if (KOREAN.equals(text)) return Locale.KOREAN;
		if (ENGLISH.equals(text)) return Locale.ENGLISH;
		if (CHINESE.equals(text)) return Locale.CHINESE;
		if (JAPANESE.equals(text)) return Locale.JAPANESE;
		
		throw new ParseException("can't parse a text to locale. text: " + text, 0);
	}

}
