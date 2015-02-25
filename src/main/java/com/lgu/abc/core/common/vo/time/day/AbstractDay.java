package com.lgu.abc.core.common.vo.time.day;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import lombok.Data;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.common.vo.time.DayOfWeek;
import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.support.bundle.MessageBundleManager;

public abstract @Data class AbstractDay {

	private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
	
	private static final String SOLAR = "common.date.solar";
	private static final String LUNAR = "common.date.lunar";
	
	protected static final String DELIMITER = "/";
	
	private String date;
	
	private boolean lunar;
	
	@JsonIgnore
	private Locale locale = Locale.KOREAN;
	
	public AbstractDay() {}
	
	public AbstractDay(String date) {
		this(date, false);
	}
	
	public AbstractDay(String date, boolean lunar) {
		setDate(date);
		this.lunar = lunar;
	}
	
	public void setDate(String date) {
		this.date = parse(date);
	}
	
	public String getCalendarType() {
		return MessageBundleManager.get(lunar ? LUNAR : SOLAR, locale);
	}
	
	public Time toTime() {
		return date == null ? null : new Time(date);
	}
	
	public Date toDate() {
		return date == null ? null : toTime().toDate();
	}
	
	public DayOfWeek dayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate());
		
		return DayOfWeek.value(calendar.get(Calendar.DAY_OF_WEEK));
	}
	
	public String date() {
		return getDate();
	}
	
	public String year() {
		return date().substring(0, 4);
	}
	
	public String month() {
		return date().substring(4, 6);
	}
	
	public String day() {
		return date().substring(6, 8);
	}
		
	public LocalDate toLocalDate() {
		return LocalDate.parse(date(), formatter);
	}
	
	public String asSolarDate() {
		throw new UnsupportedOperationException();
	}
	
	public String asLunarDate() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Hook this method in a child class if needed.
	 * @param date a date to parse
	 * @return the parsing result
	 */
	public String parse(String date) {
		if (date == null) return null;
		return date.replace(DELIMITER, "").trim();
	}
	
	@JsonIgnore
	public boolean isEmpty() {
		return StringUtils.isEmpty(date);
	}
	
	public boolean identical(AbstractDay day) {
		return date.equals(day.date) && lunar == day.lunar;
	}
	
	public abstract String getText();
	
}
