package com.lgu.abc.core.support.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.format.Formatter;

import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.common.vo.Size.Unit;

public class SizeFormatter implements Formatter<Size> {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public String print(Size object, Locale locale) {
		return String.valueOf(object.asMB());		// Print with MB by default.
	}

	@Override
	public Size parse(String text, Locale locale) throws ParseException {
		try {
			logger.trace("Size text: " + text);
			return new Size(Integer.parseInt(text), Unit.MB);
		}
		catch (NumberFormatException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}
	
	public static void main(String[] args) throws Exception {
		SizeFormatter formatter = new SizeFormatter();
		System.out.println(Integer.parseInt("15001"));
		System.out.println(formatter.parse("15001", null));
	}

}
