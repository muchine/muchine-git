package com.lgu.abc.core.support.formatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 * Formats datetime object based on user time configuration.
 * 
 * @author sejoon
 */
@Component
public class DateFormatter implements Formatter<Date> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final DateTimeFormatter format;
	
	public DateFormatter() {
		this("yyyy-MM-dd HH:mm:ss");
	}
	
	public DateFormatter(String format) {
		this.format = DateTimeFormat.forPattern(format);
	}

	@Override
	public String print(Date object, Locale locale) {
		// TODO add logic to format date based on user time configuration
		// For now, return yyyy-mm-dd HH:mm:ss style by default
		return object == null ? "" : format.print(object.getTime());
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		try {
			return StringUtils.isEmpty(text) ? null : format.parseDateTime(text).toDate();
		}
		catch (Exception e) {
			logger.debug("exception on parsing date : " + text);
			throw new ParseException(e.getMessage(), 0);
		}
		
	}

}
