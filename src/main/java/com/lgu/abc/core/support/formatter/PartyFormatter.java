package com.lgu.abc.core.support.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.prototype.org.user.User;

@Component
public class PartyFormatter implements Formatter<Party> {

	@Autowired
	private UserNameFormatter userFormatter;
	
	@Autowired
	private NameFormatter nameFormatter;
	
	@Override
	public String print(Party object, Locale locale) {
		if (object == null) return "";
		if (User.class.isAssignableFrom(object.getClass())) return userFormatter.print((User) object, locale);
		
		return nameFormatter.print(object.getName(), locale);
	}

	@Override
	public Party parse(String text, Locale locale) throws ParseException {
		return null;
	}

}
