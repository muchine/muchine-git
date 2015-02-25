package com.lgu.abc.core.common.vo.time.period;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import com.lgu.abc.core.common.vo.time.Time;

public @Data class DatePeriod {

	@NotNull @Length(min=8, max=8)
	private String start;
	
	@NotNull @Length(min=8, max=8)
	private String end;
	
	public DatePeriod() {}
	
	public DatePeriod(String start, String end) {
		this.start = start;
		this.end = end;
	}
	
	public DatePeriod(Time start, Time end) {
		this.start = start.date();
		this.end = end.date();
	}
	
	public DatePeriod(Period period) {
		this(period.start(), period.end());
	}
	
	public Period asPeriod() {
		return new Period(new Time(start, "000000"), new Time(end, "235959"));
	}
	
}
