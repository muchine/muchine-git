package com.lgu.abc.core.common.vo;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import lombok.Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Support multi language. All domain object fields that need multi-lingual support should be inherited from this class.
 * 
 * @author sejoon
 *
 */
public final @Data class Name implements Comparable<Name> {
	
	private static final String baseName = "bundles/bundle";
	
	@JsonIgnore
	protected final Log logger = LogFactory.getLog(getClass());
	
	@JsonIgnore
	private Locale locale = Locale.KOREAN;
	
	@JsonIgnore
	private String korean;
	
	@JsonIgnore
	private String english;
	
	@JsonIgnore
	private String chinese;
	
	@JsonIgnore
	private String japanese;
	
	public Name() {}
	
	public Name(Name name) {
		this.korean = name.korean;
		this.english = name.english;
		this.chinese = name.chinese;
		this.japanese = name.japanese;
	}
	
	public static Name create(String text) {
		return create(text, text, text, text);
	}
		
	public static Name create(String korean, String english, String chinese, String japanese) {
		Name name = new Name();
		
		name.setKorean(korean);
		name.setEnglish(english);
		name.setChinese(chinese);
		name.setJapanese(japanese);
		
		return name;
	}
	
	public static Name fromBundle(String key) {
		return Name.create(
				getFromBundle(key, Locale.KOREAN), 
				getFromBundle(key, Locale.ENGLISH),
				getFromBundle(key, Locale.CHINESE),
				getFromBundle(key, Locale.JAPANESE));
	}
	
	@JsonIgnore
	public boolean isNull() {
		return getValue() == null;
	}
	
	@JsonValue
	public String getValue() {
		logger.trace("locale: " + locale);
		return getValue(locale);
	}
	
	public String getValue(Locale locale) {
		if (locale == null) locale = Locale.KOREAN;
		
		if (Locale.KOREAN.equals(locale)) return korean;
		if (Locale.ENGLISH.equals(locale)) return english;
		if (Locale.CHINESE.equals(locale)) return chinese;
		if (Locale.JAPANESE.equals(locale)) return japanese;
		
		throw new IllegalStateException("unsupported locale: " + locale);
	}
	
	public Name concatenate(Name name) {
		return Name.create(
				this.korean + name.korean, 
				this.english + name.english, 
				this.chinese + name.chinese, 
				this.japanese + name.japanese);
	}
	
	@Override
	public String toString() {
		return getValue();
	}
	
	private static String getFromBundle(String key, Locale locale) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
			String value = bundle.getString(key);
			
			return StringUtils.isEmpty(value) ? "" : value;
		}
		catch (MissingResourceException e) {
			return null;
		}
	}

	@Override
	public int compareTo(Name o) {
		return this.toString().compareTo(o.toString());
	}

}
