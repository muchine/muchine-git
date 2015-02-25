package com.lgu.abc.core.common.vo.time.period;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import com.lgu.abc.core.base.utils.TimeUtils;
import com.lgu.abc.core.common.vo.time.Time;

public @Data class MonthPeriod {

	@NotNull @Length(min=6, max=6)
	private String start;
	
	@NotNull @Length(min=6, max=6)
	private String end;
	
	public MonthPeriod() {}
	
	public MonthPeriod(String start, String end) {
		this.start = start;
		this.end = end;
	}
	
	public Period asPeriod() {
		return new Period(
			new Time(TimeUtils.getFirstDayOfMonth(start).date(), "000000"), 
			new Time(TimeUtils.getLastDayOfMonth(end).date(), "235959")
		);
	}
	
	public boolean isEmpty() {
		return start == null || end == null;
	}
	
}
