package com.lgu.abc.core.common.vo.time.day;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.joda.time.LocalDate;
import org.springframework.util.Assert;

import com.lgu.abc.core.base.utils.TimeUtils;
import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.common.vo.time.period.Period;
import com.lgu.abc.core.support.validator.DateTimeValidator;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class Day extends AbstractDay {
	
	public Day() {}
	
	public Day(Date date) {
		this(new Time(date));
	}
	
	public Day(LocalDate date) {
		this(date.toDate());
	}
	
	public Day(Time time) {
		this(time.date());
	}
	
	public Day(String date) {
		this(date, false);
	}
	
	public Day(String date, boolean lunar) {
		super(date, lunar);
		Assert.isTrue(DateTimeValidator.validateDate(date), "invalid date: " + date);
	}
	
	@Pattern(regexp = "^[0-9]{8}$")
	public void setDate(String date) {
		super.setDate(date);
	}
	
	public String getText() {
		if (getDate() == null) return null;
		if (getDate().length() != 8) return getDate();
		
		StringBuilder builder = new StringBuilder();
		builder
			.append(year())
			.append(DELIMITER)
			.append(month())
			.append(DELIMITER)
			.append(day());
		
		return builder.toString();
	}
		
	public static List<Day> asList(String month) {
		Day start = TimeUtils.getFirstDayOfMonth(month);		
		Day end = TimeUtils.getLastDayOfMonth(month);
		
		return asList(start, end);
	}
	
	public static List<Day> asList(Period period) {
		return asList(new Day(period.start()), new Day(period.end()));
	}
	
	public static List<Day> asList(Day start, Day end) {
		List<Day> days = new ArrayList<Day>();
		for (LocalDate date = start.toLocalDate(), max = end.toLocalDate(); date.isBefore(max); date = date.plusDays(1)) {
			days.add(new Day(date));
		}
		
		days.add(end);
		return days;
	}
	
}
