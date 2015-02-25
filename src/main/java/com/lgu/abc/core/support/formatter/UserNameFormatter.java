package com.lgu.abc.core.support.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.formatter.factory.UserFormattable;
import com.lgu.abc.core.support.formatter.factory.UserFormattableFactory;

/**
 * Format user name based on configuration. Show user's name, position, department, etc. with given configuration.
 * 
 * @author sejoon
 *
 */
@Component
public class UserNameFormatter implements Formatter<User> {
	
	private static UserNameFormatter instance;
	
	@Autowired
	private UserFormattableFactory factory;
		
	UserNameFormatter() {
		initialize();
	}
	
	private synchronized void initialize() {
		if (instance == null) instance = this;
	}
	
	@Override
	public String print(User user, Locale locale) {
		UserFormattable formatter = factory.create();
		return formatter.format(user, locale);
	}

	@Override
	public User parse(String text, Locale locale) throws ParseException {
		return null;
	}
	
	public String designate(User user, Locale locale) {
		UserFormattable formatter = factory.create();
		return formatter.designate(user, locale);
	}	
	
	public static final UserNameFormatter instance() {
		return instance;
	}
	
}
