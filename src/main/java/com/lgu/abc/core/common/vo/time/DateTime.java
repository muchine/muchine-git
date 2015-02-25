package com.lgu.abc.core.common.vo.time;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.lgu.abc.core.common.vo.time.day.Day;

public @Data class DateTime implements Comparable<DateTime> {

	@NotNull @Length(min=8, max=8)
	private String date;	
	
	@NotNull @Length(min=6, max=6)
	private String time;
	
	public DateTime() {}
	
	public DateTime(String date, String time) {
		this.date = date;
		this.time = time;
	}
	
	public DateTime(Time time) {
		this.date = time.date();
		this.time = time.time();
	}
	
	public DateTime(Date date) {
		this(new Time(date));
	}
	
	public DateTime(DateTime time) {
		this.date = time.date;
		this.time = time.time;
	}
	
	public String getHour() {
		if (StringUtils.isEmpty(time)) return "";
		return time.substring(0, 2);
	}
	
	public String getMinute() {
		if (StringUtils.isEmpty(time)) return "";
		return time.substring(2, 4);
	}
		
	public Time asTime() {
		return new Time(date, time);
	}
	
	public Day asDay() {
		return new Day(date);
	}

	@Override
	public int compareTo(DateTime o) {
		return asTime().compareTo(o.asTime());
	}
		
}
