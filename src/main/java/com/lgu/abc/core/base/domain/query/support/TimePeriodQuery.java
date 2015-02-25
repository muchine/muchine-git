package com.lgu.abc.core.base.domain.query.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

import org.springframework.util.Assert;

import com.lgu.abc.core.base.utils.TimeUtils;
import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.common.vo.time.day.Day;
import com.lgu.abc.core.common.vo.time.period.Period;

public @Data class TimePeriodQuery {
	
	private static final String MIN_START_DATE 	= "19700101";
	private static final String MAX_END_DATE		= "21001231";
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private final Date start;
	
	private final Date end;
	
	public TimePeriodQuery() {
		this(new Day(MIN_START_DATE), new Day(MAX_END_DATE));
	}
	
	public TimePeriodQuery(Period period) {
		this(period.start(), period.end());
	}
	
	public TimePeriodQuery(Time start, Time end) {
		this(start.toDate(), end.toDate());
	}
	
	public TimePeriodQuery(Date start, Date end) {
		Assert.isTrue(!end.before(start));
		
		this.start = start;
		this.end = end;
	}
	
	public TimePeriodQuery(Day start, Day end) {
		try {
			this.start = formatter.parse(start.date() + "000000");
			this.end = formatter.parse(end.date() + "235959");	
		}
		catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public TimePeriodQuery(Day date) {
		this(date, date);
	}
	
	public TimePeriodQuery(String month) {
		this(TimeUtils.getFirstDayOfMonth(month), TimeUtils.getLastDayOfMonth(month));
	}
	
}
