package com.lgu.abc.core.support.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.vo.Name;

/**
 * Formats name object based on user configuration for multi-lingual support.
 *  
 * @author sejoon
 *
 */
@Component
public class NameFormatter implements Formatter<Name> {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private Locale locale;
	
	/*
	 * This constructor will be used by Spring Application Context to create an instance.
	 * If instantiation of this class is needed on other codes, use static factory method with locale.
	 */
	private NameFormatter() {}
	
	private NameFormatter(Locale locale) {
		this.locale = locale;
	}
	
	@Override
	public String print(Name object, Locale locale) {
		if (object == null) return "";	
		return object.getValue(locale());
	}

	@Override
	public Name parse(String text, Locale locale) throws ParseException {
		logger.trace("formatting name... text: " + text);
		return Name.create(text);
	}
	
	private Locale locale() {
		if (locale != null) return locale;
		return Locale.KOREAN;
//		User user = sessionUtil.getSession();
//		return user == null ? Locale.KOREAN : user.getLocale(); 
	}
	
	public static NameFormatter fromLocale(Locale locale) {
		return new NameFormatter(locale);
	}
	
}
