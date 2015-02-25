package com.lgu.abc.core.common.vo.time;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.lgu.abc.core.support.validator.DateTimeValidator;

/**
 * Class representing specific time instance. Implemented interally by Joda {@link DateTime}.
 * @author sejoon
 * @see DateTime
 *
 */
public final class Time implements Comparable<Time> {
	
	private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
		
	private final DateTime datetime;
	
	public Time() {
		this(new Date());
	}
	
	public Time(String date) {
		this(date, "000000");
	}
	
	public Time(String date, String time) {
		if (!DateTimeValidator.validateDate(date))
			throw new IllegalArgumentException("Invalid date format: " + date);

		if (!DateTimeValidator.validateTime(time))
			throw new IllegalArgumentException("Invalid time format: " + time);
		
		this.datetime = formatter.parseDateTime(date + time);
	}
	
	public Time(Time date) {
		this.datetime = date.datetime;
	}
	
	public Time(Date date) {
		Validate.notNull(date, "date is null.");
		this.datetime = new DateTime(date);
	}
	
	public Time(DateTime datetime) {
		this.datetime = datetime;
	}
	
	public Time(Calendar calendar) {
		this.datetime = new DateTime(calendar);
	}
	
	public String year() {
		return print().substring(0, 4);
	}
	
	public String month() {
		return print().substring(4, 6);
	}
	
	public String day() {
		return print().substring(6, 8);
	}
	
	public String date() {
		return print().substring(0, 8);
	}
	
	public String time() {
		return print().substring(8, 14);
	}
	
	public String hours() {
		return print().substring(8, 10);
	}
	
	public String minutes() {
		return print().substring(10, 12);
	}
	
	public String seconds() {
		return print().substring(12, 14);
	}
	
	public String print() {
		return formatter.print(this.datetime);
	}
	
	/*
	 * Comparator methods
	 */
	public boolean before(Time date) {
		if (date == null) return false;
		return this.datetime.compareTo(date.datetime) < 0;
	}
	
	public boolean after(Time date) {
		if (date == null) return false;
		return this.datetime.compareTo(date.datetime) > 0;
	}
	
	public Time plusDays(int days) {
		return new Time(this.datetime.plusDays(days));
	}
	
	public Time plusMinutes(int minutes) {
		return new Time(datetime.plusMinutes(minutes));
	}
	
	public Time plusSeconds(int seconds) {
		return new Time(datetime.plusSeconds(seconds));
	}
	
	public Time minusDays(int days) {
		return new Time(datetime.minusDays(days));
	}
	
	public Time minusMinutes(int minutes) {
		return new Time(datetime.minusMinutes(minutes));
	}
	
	public Time minusSeconds(int seconds) {
		return new Time(datetime.minusSeconds(seconds));
	}
	
	public Date toDate() {
		return this.datetime.toDate();
	}
	
	public LocalDate toLocalDate() {
		return this.datetime.toLocalDate();
	}
	
	public DateTime toDateTIme() {
		return this.datetime;
	}
	
	public String toString() {
		return print();
	}
	
	@Override
	public int compareTo(Time o) {
		return this.datetime.compareTo(o.datetime);
	}
	
	@Override
	public int hashCode() {
		return datetime.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Time)) return false;
		
		Time time = (Time) obj;
		return print().equals(time.print());
	}

}
