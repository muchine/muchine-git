package com.lgu.abc.core.support.formatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.junit.Test;

import com.lgu.abc.core.common.vo.Name;

public class NameFormatterTest {

	private final NameFormatter formatter = NameFormatter.fromLocale(Locale.KOREAN);
	
	private final Name name = Name.create("Korean", "English", "Chinese", "Japanese");
	
	@Test
	public void testPrintNullName() {
		assertThat(formatter.print(null, null), is(""));
	}
	
	@Test
	public void testPrintNameWithUserLanguage() {
		assertThat(formatter.print(name, null), is("Korean"));
	}
	
	@Test
	public void testPrintNameWithAssigneLanguage() {
		NameFormatter formatter = NameFormatter.fromLocale(Locale.JAPANESE);
		assertThat(formatter.print(name, null), is("Japanese"));
	}
	
	@Test
	public void testParseNullName() throws Exception {
		assertThat(formatter.parse(null, null), is(Name.create(null)));
	}
	
	@Test
	public void testParseName() throws Exception {
		Name name = formatter.parse("Sejoon", null);
		assertThat(name, is(Name.create("Sejoon", "Sejoon", "Sejoon", "Sejoon")));
	}
		
}
