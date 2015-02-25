package com.lgu.abc.core.base.domain.query.support;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.common.vo.time.day.Day;
import com.lgu.abc.core.common.vo.time.period.Period;
import com.lgu.abc.core.support.validator.DateTimeValidator;

public @Data class DatePeriodQuery {
	
	public static final String MIN_START_DATE	= "19700101";
	public static final String MAX_END_DATE	= "21001231";
	
	@NotNull @Length(min=8, max=8)
	private String start;
	
	@NotNull @Length(min=8, max=8)
	private String end;
	
	public DatePeriodQuery() {
		this(MIN_START_DATE, MAX_END_DATE);
	}
	
	public DatePeriodQuery(Period period) {
		this(period.start().date(), period.end().date());
	}
	
	public DatePeriodQuery(Time start, Time end) {
		this(new Period(start, end));
	}
	
	public DatePeriodQuery(Day start, Day end) {
		this(start.date(), end.date());
	}
	
	public DatePeriodQuery(String start, String end) {
		Assert.isTrue(DateTimeValidator.validateDate(start));
		Assert.isTrue(DateTimeValidator.validateDate(end));
		Assert.isTrue(start.compareTo(end) <= 0);
		
		this.start = start;
		this.end = end;
	}
		
	public void clear() {
		this.start = MIN_START_DATE;
		this.end = MAX_END_DATE;
	}
	
	public Day start() {
		return new Day(start);
	}
	
	public Day end() {
		return new Day(end);
	}
	
	public TimePeriodQuery asTimePeriodQuery() {
		return new TimePeriodQuery(new Day(start), new Day(end));
	}
	
	public List<Day> asDays() {
		return Day.asList(start(), end());
	}
	
}
