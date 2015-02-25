package com.lgu.abc.core.support.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.vo.contact.domain.PhoneNumber;

/**
 * Formats phone number object.
 * 
 * @author sejoon
 */
@Component
public class PhoneNumberFormatter implements Formatter<PhoneNumber> {

	@Override
	public String print(PhoneNumber object, Locale locale) {
		return object.toString();
	}

	@Override
	public PhoneNumber parse(String text, Locale locale) throws ParseException {
		return new PhoneNumber(text);
	}

}
